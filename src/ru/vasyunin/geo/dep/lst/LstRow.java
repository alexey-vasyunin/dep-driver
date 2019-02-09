package ru.vasyunin.geo.dep.lst;

import ru.vasyunin.geo.dep.ByteArrayInputStreamExtended;
import ru.vasyunin.geo.dep.DepException;

import java.io.IOException;

public class LstRow {
    private long seek_file;
    private float depth;
    private long datetime;
    private long n;
    private long nb;
    private int omen;

    public long getSeek_file() {
        return seek_file;
    }

    public float getDepth() {
        return depth;
    }

    public long getDatetime() {
        return datetime;
    }

    public long getN() {
        return n;
    }

    public long getNb() {
        return nb;
    }

    public int getOmen() {
        return omen;
    }

    public LstRow(byte[] buffer) throws DepException, IOException {
        setRow(buffer);
    }

    public void setRow(byte[] buffer) throws DepException, IOException {
        try (ByteArrayInputStreamExtended dis = new ByteArrayInputStreamExtended(buffer)) {
            seek_file = dis.readUnsignedIntLE();
            depth = dis.readUnsignedFloatLE();
            datetime = dis.readUnsignedIntLE();
            n = dis.readUnsignedIntLE();
            nb = dis.readUnsignedIntLE();
            omen = dis.readUnsignedByteLE();
        }
    }

}
