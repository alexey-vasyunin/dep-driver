package ru.vasyunin.geo.dep;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteArrayInputStreamExtended extends ByteArrayInputStream {
    public ByteArrayInputStreamExtended(byte[] buf) {
        super(buf);
    }

    public ByteArrayInputStreamExtended(byte[] buf, int offset, int length) {
        super(buf, offset, length);
    }

    public final int readUnsignedShortLE() throws IOException {
        int ch1 = this.read();
        int ch2 = this.read();
        if ((ch1 | ch2) < 0)
            throw new EOFException();
        return (ch2 << 8) + (ch1 << 0);
    }

    public final long readUnsignedIntLE() throws IOException {
        int ch1 = this.read();
        int ch2 = this.read();
        int ch3 = this.read();
        int ch4 = this.read();
        if ((ch1 | ch2 | ch3 | ch4) < 0)
            throw new EOFException();
        return ((long) ch4 & 0xff) << 24
                | ((long) ch3 & 0xff) << 16
                | ((long) ch2 & 0xff) << 8
                | ((long) ch1 & 0xff);
    }

    public final int readUnsignedByteLE() throws IOException {
        int ch = this.read();
        if (ch < 0)
            throw new EOFException();
        return ch;
    }

    public final float readUnsignedFloatLE() throws IOException {
        byte[] buf = new byte[4];
        int ch = read(buf);
        if (ch < 4) throw new EOFException();
        return ByteBuffer.wrap(buf).order(ByteOrder.LITTLE_ENDIAN).getFloat();
    }
}
