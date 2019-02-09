package ru.vasyunin.geo.dep;

import ru.vasyunin.geo.dep.parsys.ParSys;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Dep implements Iterable<DepRowStruct>{
    private String dataFile;
    private Path indexFile;
    private ParSys parSys;

    private byte[] buffer;
    private long currentDepOffset = 0;

    private RandomAccessFileExtended rafeDataFile;

    public Dep(String dataDepFilename, String indexLstFilename, String parsysDirname) throws DepException {
        dataFile = dataDepFilename;
        indexFile = Paths.get(indexLstFilename);

        parSys = new ParSys(parsysDirname);
        buffer = new byte[8192];
    }

    public boolean open() {
        try {
            rafeDataFile = new RandomAccessFileExtended(dataFile, "r");
            rafeDataFile.seek(0);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean close(){
        try {
            rafeDataFile.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private DepRowStruct readRow() throws IOException, DepException {
        currentDepOffset = rafeDataFile.getFilePointer();
        if (rafeDataFile.skipBytes(6) != 6) throw new NoSuchElementException();
        int reclength = rafeDataFile.readUnsignedShortLE();
        rafeDataFile.seek(currentDepOffset);
        if (rafeDataFile.read(buffer, 0, reclength) < reclength) {
            throw new DepException("Can't read record");
        }
        currentDepOffset = rafeDataFile.getFilePointer();
        return new DepRowStruct(buffer, parSys);
    }


    private boolean isFinished() throws IOException {
        return rafeDataFile.length() > currentDepOffset;
    }
    @Override
    public Iterator<DepRowStruct> iterator() {
        return new DepIterator(this);
    }

    @Override
    public void forEach(Consumer<? super DepRowStruct> action) {

    }

    @Override
    public Spliterator<DepRowStruct> spliterator() {
        return null;
    }


    public void setCurrentDepOffset(long currentDepOffset) {
        this.currentDepOffset = currentDepOffset;
    }

    static class DepIterator implements Iterator<DepRowStruct> {
        private Dep dep;

        @Override
        public boolean hasNext() {
            try {
                return dep.isFinished();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public DepRowStruct next() {
            try {
                return dep.readRow();
            } catch (DepException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public DepIterator(Dep dep) {
            this.dep = dep;
            dep.setCurrentDepOffset(0);
        }
    }
}
