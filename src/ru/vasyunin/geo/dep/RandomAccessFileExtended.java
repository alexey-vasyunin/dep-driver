package ru.vasyunin.geo.dep;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;

public class RandomAccessFileExtended extends RandomAccessFile {
    public RandomAccessFileExtended(String name, String mode) throws FileNotFoundException {
        super(name, mode);
    }

    /**
     * Reads an unsigned 32-bit number from this file. This method reads
     * four bytes from the file, starting at the current file pointer.
     * This method blocks until the two bytes are read, the end of the
     * stream is detected, or an exception is thrown.
     *
     * @return     the next four bytes of this file, interpreted as an unsigned
     *             32-bit long.
     * @exception  EOFException  if this file reaches the end before reading
     *               four bytes.
     * @exception  IOException   if an I/O error occurs.
     */



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


    /**
     * Reads an unsigned 16-bit number from this file in Little Endian. This method reads
     * two bytes from the file, starting at the current file pointer.
     * This method blocks until the two bytes are read, the end of the
     * stream is detected, or an exception is thrown.
     *
     * @return     the next two bytes of this file, interpreted as an unsigned
     *             16-bit integer.
     * @exception  EOFException  if this file reaches the end before reading
     *               two bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    public final int readUnsignedShortLE() throws IOException {
        int ch1 = this.read();
        int ch2 = this.read();
        if ((ch1 | ch2) < 0)
            throw new EOFException();
        return (ch2 << 8) + (ch1 << 0);
    }
}
