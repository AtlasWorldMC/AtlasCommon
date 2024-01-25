package fr.atlasworld.common.logging.stream;

import com.google.common.base.Preconditions;
import fr.atlasworld.common.logging.Level;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Redirects an {@link OutputStream} info {@code SLF4J} logging messages.
 * @author itshorty
 * (<a href="https://stackoverflow.com/questions/11187461/redirect-system-out-and-system-err-to-slf4j">Original Post</a>)
 */
public class LoggingOutputStream extends OutputStream {
    private static final int DEFAULT_BUFFER_LENGTH = 2048;
    private static final String LINE_SEPARATOR = System.lineSeparator();

    protected final Logger logger;
    protected final Level loggingLevel;

    protected boolean closed = false;

    /**
     * The internal buffer where data is stored.
     */
    protected byte[] buf;

    /**
     * Remembers the size of the buffer for speed.
     */
    private int bufLength;

    /**
     * The number of valid bytes in the buffer. This value is always in the
     * range <code>0</code> through <code>buf.length</code>; elements
     * <code>buf[0]</code> through <code>buf[count-1]</code> contain valid byte
     * data.
     */
    protected int count;

    public LoggingOutputStream(@NotNull Logger logger, @NotNull Level loggingLevel) {
        Preconditions.checkNotNull(logger);
        Preconditions.checkNotNull(loggingLevel);

        this.logger = logger;
        this.loggingLevel = loggingLevel;

        this.bufLength = DEFAULT_BUFFER_LENGTH;
        this.buf = new byte[DEFAULT_BUFFER_LENGTH];
        this.count = 0;
    }

    /**
     * Writes the specified byte to this output stream. The general contract
     * for <code>write</code> is that one byte is written to the output
     * stream. The byte to be written is the eight low-order bits of the
     * argument <code>b</code>. The 24 high-order bits of <code>b</code> are
     * ignored.
     *
     * @param b
     *            the <code>byte</code> to write
     */
    @Override
    public void write(int b) throws IOException {
        if (this.closed)
            throw new IOException("Logging stream closed.");

        // don't logger nulls
        if (b == 0) {
            return;
        }

        // would this be writing past the buffer?
        if (this.count == this.bufLength) {
            // grow the buffer
            final int newBufLength = this.bufLength + DEFAULT_BUFFER_LENGTH;
            final byte[] newBuf = new byte[newBufLength];

            System.arraycopy(this.buf, 0, newBuf, 0, this.bufLength);

            this.buf = newBuf;
            this.bufLength = newBufLength;
        }

        this.buf[this.count] = (byte) b;
        count++;
    }


    /**
     * Flushes this output stream and forces any buffered output bytes to be
     * written out. The general contract of <code>flush</code> is that
     * calling it is an indication that, if any bytes previously written
     * have been buffered by the implementation of the output stream, such
     * bytes should immediately be written to their intended destination.
     */
    @Override
    public void flush() {
        if (count == 0) {
            return;
        }

        // don't print out blank lines; flushing from PrintStream puts out
        // these
        if (this.count == LINE_SEPARATOR.length()) {
            if (((char) this.buf[0]) == LINE_SEPARATOR.charAt(0) && ((this.count == 1) ||
                    ((this.count == 2) && ((char) buf[1]) == LINE_SEPARATOR.charAt(1)))) {
                this.reset();
                return;
            }
        }

        final byte[] messageBytes = new byte[this.count];

        System.arraycopy(this.buf, 0, messageBytes, 0, this.count);

        this.logger.atLevel(this.loggingLevel.convertToSlf4J()).log(new String(messageBytes).trim());

        this.reset();
    }

    private void reset() {
        // not resetting the buffer -- assuming that if it grew that it
        // will likely grow similarly again
        this.count = 0;
    }

    @Override
    public void close() throws IOException {
        this.flush();
        this.closed = true;
    }
}
