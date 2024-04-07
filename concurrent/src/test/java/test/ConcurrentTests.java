package test;

import fr.atlasworld.common.concurrent.action.CompositeFutureAction;
import fr.atlasworld.common.concurrent.action.SimpleFutureAction;
import org.junit.jupiter.api.*;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Concurrency Tests")
public class ConcurrentTests {
    static ExecutorService executor;

    @BeforeAll
    static void setup() {
        executor = Executors.newFixedThreadPool(10);
    }

    @AfterAll
    static void cleanup() {
        executor.shutdownNow();
    }

    @Nested
    @DisplayName("Simple Future Action Tests")
    class SimpleFutureActionTests {

        @Nested
        @DisplayName("Succeeds")
        class SimpleFutureActionSuccessTests {

            @Test
            @DisplayName("with value available")
            void testSuccess() {
                String expectedResult = "success message";
                SimpleFutureAction<String> future = new SimpleFutureAction<>();

                executor.submit(() -> {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ignored) {
                    }

                    future.complete(expectedResult);
                });

                future.syncUninterruptibly();

                assertTrue(future.success());
                assertEquals(future.result(), expectedResult);
            }

            @Test
            @DisplayName("does not timeout")
            void testTimeoutFailure() {
                SimpleFutureAction<Void> future = new SimpleFutureAction<>();

                executor.submit(() -> {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ignored) {
                    }

                    future.complete(null);
                });

                future.timeout(30, TimeUnit.MILLISECONDS);
                future.syncUninterruptibly();

                assertTrue(future.success());
            }

            @Test
            @DisplayName("and onSuccess called")
            void testTimeoutOnSuccessCalled() {
                SimpleFutureAction<Void> future = new SimpleFutureAction<>();

                AtomicBoolean called = new AtomicBoolean(false);
                future.onSuccess(var -> called.set(true));

                executor.submit(() -> {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException ignored) {
                    }

                    future.complete(null);
                });

                future.syncUninterruptibly();

                assertTrue(future.success());
                assertTrue(called.get());
            }

            @Test
            @DisplayName("and whenDone called")
            void testTimeoutWhenCalled() {
                SimpleFutureAction<Void> future = new SimpleFutureAction<>();

                AtomicBoolean called = new AtomicBoolean(false);
                future.whenDone((var, cause) -> called.set(true));

                executor.submit(() -> {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException ignored) {
                    }

                    future.complete(null);
                });

                future.syncUninterruptibly();

                assertTrue(future.success());
                assertTrue(called.get());
            }
        }

        @Nested
        @DisplayName("Fails")
        class SimpleFutureActionFailureTests {

            @Test
            @DisplayName("with thrown exception")
            void testFailure() {
                Throwable expectedCause = new GeneralSecurityException();
                SimpleFutureAction<Void> future = new SimpleFutureAction<>();

                executor.submit(() -> {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ignored) {
                    }

                    future.fail(expectedCause);
                });

                future.syncUninterruptibly();

                assertFalse(future.success());
                assertEquals(future.cause(), expectedCause);
            }

            @Test
            @DisplayName("and onFailure called")
            void testTimeoutOnCancelledCalled() {
                SimpleFutureAction<Void> future = new SimpleFutureAction<>();

                AtomicBoolean called = new AtomicBoolean(false);
                future.onFailure(cause -> called.set(true));

                executor.submit(() -> {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException ignored) {
                    }

                    future.fail(new Throwable());
                });

                future.syncUninterruptibly();

                assertFalse(future.success());
                assertTrue(called.get());
            }

            @Test
            @DisplayName("and whenDone called")
            void testTimeoutWhenCalled() {
                SimpleFutureAction<Void> future = new SimpleFutureAction<>();

                AtomicBoolean called = new AtomicBoolean(false);
                future.whenDone((var, cause) -> called.set(true));

                executor.submit(() -> {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException ignored) {
                    }

                    future.fail(new Throwable());
                });

                future.syncUninterruptibly();

                assertFalse(future.success());
                assertTrue(called.get());
            }
        }

        @Nested
        @DisplayName("Times-out")
        class SimpleFutureActionTimeoutTests {

            @Test
            @DisplayName("with timeout exception")
            void testTimeoutFailure() {
                SimpleFutureAction<Void> future = new SimpleFutureAction<>();

                executor.submit(() -> {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException ignored) {
                    }

                    future.complete(null);
                });

                future.timeout(10, TimeUnit.MILLISECONDS);
                future.syncUninterruptibly();

                assertFalse(future.success());
                assertInstanceOf(TimeoutException.class, future.cause());
            }

            @Test
            @DisplayName("and onCancelled called")
            void testTimeoutOnCancelledCalled() {
                SimpleFutureAction<Void> future = new SimpleFutureAction<>();

                AtomicBoolean called = new AtomicBoolean(false);
                future.onCancellation(interrupt -> {
                    called.set(true);
                    return called.get();
                });

                executor.submit(() -> {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException ignored) {
                    }

                    future.complete(null);
                });

                future.timeout(10, TimeUnit.MILLISECONDS);
                future.syncUninterruptibly();

                assertFalse(future.success());
                assertTrue(called.get());
            }

            @Test
            @DisplayName("and cancelled")
            void testTimeoutCancelled() {
                SimpleFutureAction<Void> future = new SimpleFutureAction<>();

                future.onCancellation(interrupt -> true);

                executor.submit(() -> {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException ignored) {
                    }

                    future.complete(null);
                });

                future.timeout(10, TimeUnit.MILLISECONDS);
                future.syncUninterruptibly();

                assertFalse(future.success());
                assertTrue(future.isCancelled());
            }
        }
    }

    @Nested
    @DisplayName("Composite Future Action Tests")
    class CompositeFutureActionTests {
        static final int FUTURE_AMOUNT = 10;

        List<SimpleFutureAction<Void>> futures;
        CompositeFutureAction future;

        @BeforeEach
        void setUp() {
            futures = new ArrayList<>();

            for (int i = 0; i < FUTURE_AMOUNT; i++) {
                futures.add(new SimpleFutureAction<>());
            }

            CompositeFutureAction.CompositeBuilder builder = CompositeFutureAction.builder();
            futures.forEach(builder::add);

            future = builder.build();
        }

        @Nested
        @DisplayName("Succeeds")
        class CompositeFutureActionSuccessTests {

            @Test
            @DisplayName("does not timeout")
            void testTimeoutFailure() {
                executor.submit(() -> {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ignored) {
                    }

                    futures.forEach(future -> future.complete(null));
                });

                future.timeout(30, TimeUnit.MILLISECONDS);
                future.syncUninterruptibly();

                assertTrue(future.success());
            }

            @Test
            @DisplayName("and onSuccess called")
            void testTimeoutOnSuccessCalled() {
                AtomicBoolean called = new AtomicBoolean(false);
                future.onSuccess(unused -> called.set(true));

                executor.submit(() -> {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException ignored) {
                    }

                    futures.forEach(future -> future.complete(null));
                });

                future.syncUninterruptibly();

                assertTrue(future.success());
                assertTrue(called.get());
            }

            @Test
            @DisplayName("and whenDone called")
            void testTimeoutWhenDoneCalled() {
                AtomicBoolean called = new AtomicBoolean(false);
                future.whenDone((unused, cause) -> called.set(true));

                executor.submit(() -> {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException ignored) {
                    }

                    futures.forEach(future -> future.complete(null));
                });

                future.syncUninterruptibly();

                assertTrue(future.success());
                assertTrue(called.get());
            }
        }

        @Nested
        @DisplayName("Fails")
        class CompositeFutureActionFailureTests {

            @Test
            @DisplayName("with thrown exception")
            void testFailure() {
                Throwable expectedCause = new GeneralSecurityException();

                executor.submit(() -> {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ignored) {
                    }

                    futures.forEach(future -> future.fail(expectedCause));
                });

                future.syncUninterruptibly();

                assertFalse(future.success());
                assertNotNull(future.cause());
                assertEquals(future.cause().getCause(), expectedCause);
            }

            @Test
            @DisplayName("and onFailure called")
            void testTimeoutOnCancelledCalled() {
                AtomicBoolean called = new AtomicBoolean(false);
                future.onFailure(cause -> called.set(true));

                executor.submit(() -> {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException ignored) {
                    }

                    futures.forEach(future -> future.fail(new Throwable()));
                });

                future.syncUninterruptibly();

                assertFalse(future.success());
                assertTrue(called.get());
            }

            @Test
            @DisplayName("and whenDone called")
            void testTimeoutWhenCalled() {
                AtomicBoolean called = new AtomicBoolean(false);
                future.whenDone((unused, throwable) -> called.set(true));

                executor.submit(() -> {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException ignored) {
                    }

                    futures.forEach(future -> future.fail(new Throwable()));
                });

                future.syncUninterruptibly();

                assertFalse(future.success());
                assertTrue(called.get());
            }
        }

        @Nested
        @DisplayName("Times-out")
        class CompositeActionTimeoutTests {

            @Test
            @DisplayName("with timeout exception")
            void testTimeoutFailure() {
                executor.submit(() -> {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException ignored) {
                    }

                    futures.forEach(future -> future.complete(null));
                });

                future.timeout(10, TimeUnit.MILLISECONDS);
                future.syncUninterruptibly();

                assertFalse(future.success());
                assertInstanceOf(TimeoutException.class, future.cause());
            }

            @Test
            @DisplayName("and cancelled")
            void testTimeoutCancelled() {
                executor.submit(() -> {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException ignored) {
                    }

                    futures.forEach(future -> future.complete(null));
                });

                future.timeout(10, TimeUnit.MILLISECONDS);
                future.syncUninterruptibly();

                assertFalse(future.success());
                assertTrue(future.isCancelled());
            }
        }
    }
}
