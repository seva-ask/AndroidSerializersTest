package ru.touchin.customserializer.serializer;

import java.io.IOException;
import java.io.InputStream;

/**
 * A specialized {@link java.io.InputStream } for reading the contents of a byte array.
 *
 * @see java.io.ByteArrayOutputStream
 */
public class MyBIUS extends InputStream {
    /**
     * The {@code byte} array containing the bytes to stream over.
     */
    protected byte[] buf;

    /**
     * The current position within the byte array.
     */
    protected int pos;

    /**
     * The current mark position. Initially set to 0 or the <code>offset</code>
     * parameter within the constructor.
     */
    protected int mark;

    /**
     * The total number of bytes initially available in the byte array
     * {@code buf}.
     */
    protected int count;

    /**
     * Constructs a new {@code ByteArrayInputStream} on the byte array
     * {@code buf}.
     *
     * @param buf
     *            the byte array to stream over.
     */
    public MyBIUS(byte[] buf) {
        this.mark = 0;
        this.buf = buf;
        this.count = buf.length;
    }

    /**
     * Constructs a new {@code ByteArrayInputStream} on the byte array
     * {@code buf} with the initial position set to {@code offset} and the
     * number of bytes available set to {@code offset} + {@code length}.
     *
     * @param buf
     *            the byte array to stream over.
     * @param offset
     *            the initial position in {@code buf} to start streaming from.
     * @param length
     *            the number of bytes available for streaming.
     */
    public MyBIUS(byte[] buf, int offset, int length) {
        this.buf = buf;
        pos = offset;
        mark = offset;
        count = offset + length > buf.length ? buf.length : offset + length;
    }

    /**
     * Returns the number of remaining bytes.
     *
     * @return {@code count - pos}
     */
    @Override
    public int available() {
        return count - pos;
    }

    /**
     * Closes this stream and frees resources associated with this stream.
     *
     * @throws java.io.IOException
     *             if an I/O error occurs while closing this stream.
     */
    @Override
    public void close() throws IOException {
        // Do nothing on close, this matches JDK behavior.
    }

    /**
     * Sets a mark position in this ByteArrayInputStream. The parameter
     * {@code readlimit} is ignored. Sending {@code reset()} will reposition the
     * stream back to the marked position.
     *
     * @param readlimit
     *            ignored.
     * @see #markSupported()
     * @see #reset()
     */
    @Override
    public void mark(int readlimit) {
        mark = pos;
    }

    /**
     * Indicates whether this stream supports the {@code mark()} and
     * {@code reset()} methods. Returns {@code true} since this class supports
     * these methods.
     *
     * @return always {@code true}.
     * @see #mark(int)
     * @see #reset()
     */
    @Override
    public boolean markSupported() {
        return true;
    }

    /**
     * Reads a single byte from the source byte array and returns it as an
     * integer in the range from 0 to 255. Returns -1 if the end of the source
     * array has been reached.
     *
     * @return the byte read or -1 if the end of this stream has been reached.
     */
    @Override
    public int read() {
        return pos < count ? buf[pos++] & 0xFF : -1;
    }

    @Override public int read(byte[] buffer, int byteOffset, int byteCount) {
        checkOffsetAndCount(buffer.length, byteOffset, byteCount);

        // Are there any bytes available?
        if (this.pos >= this.count) {
            return -1;
        }
        if (byteCount == 0) {
            return 0;
        }

        int copylen = this.count - pos < byteCount ? this.count - pos : byteCount;
        System.arraycopy(this.buf, pos, buffer, byteOffset, copylen);
        pos += copylen;
        return copylen;
    }

    public static void checkOffsetAndCount(int arrayLength, int offset, int count) {
        if ((offset | count) < 0 || offset > arrayLength || arrayLength - offset < count) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Resets this stream to the last marked location. This implementation
     * resets the position to either the marked position, the start position
     * supplied in the constructor or 0 if neither has been provided.
     *
     * @see #mark(int)
     */
    @Override
    public void reset() {
        pos = mark;
    }

    /**
     * Skips {@code byteCount} bytes in this InputStream. Subsequent
     * calls to {@code read} will not return these bytes unless {@code reset} is
     * used. This implementation skips {@code byteCount} number of bytes in the
     * target stream. It does nothing and returns 0 if {@code byteCount} is negative.
     *
     * @return the number of bytes actually skipped.
     */
    @Override
    public long skip(long byteCount) {
        if (byteCount <= 0) {
            return 0;
        }
        int temp = pos;
        pos = this.count - pos < byteCount ? this.count : (int) (pos + byteCount);
        return pos - temp;
    }
}
