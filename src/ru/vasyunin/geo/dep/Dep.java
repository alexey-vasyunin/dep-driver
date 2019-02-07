package ru.vasyunin.geo.dep;

import ru.vasyunin.geo.dep.parsys.ParSys;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Dep {
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
}
