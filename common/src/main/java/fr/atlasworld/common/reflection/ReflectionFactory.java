package fr.atlasworld.common.reflection;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Reflection Factory, Utility class for common reflection method.
 */
public class ReflectionFactory {
    public static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    /**
     * Checks whether a class exists.
     * @param name fully qualified name of the class.
     *
     * @return true if it exists false otherwise
     */
    public static boolean classExists(String name) {
        try {
            Class.forName(name, false, STACK_WALKER.getCallerClass().getClassLoader());
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * Load a single service for an interface.
     *
     * @param serviceInterface interface class for which the service must be loaded.
     * @return the loaded service interface instance.
     * @throws IllegalStateException if none or multiple services we're found for this interface.
     * @param <T> interface generic of the loaded service.
     */
    public static <T> T loadSingleService(Class<T> serviceInterface) {
        Iterator<T> iterator = ServiceLoader.load(serviceInterface).iterator();

        if (!iterator.hasNext())
            throw new IllegalStateException("Could not find service for specified interface.");

        T service = iterator.next();

        if (iterator.hasNext())
            throw new IllegalStateException("Multiple services found for the specified interface.");

        return service;
    }
}
