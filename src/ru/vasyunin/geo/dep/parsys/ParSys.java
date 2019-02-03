package ru.vasyunin.geo.dep.parsys;

import ru.vasyunin.geo.dep.RandomAccessFileExtended;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

public class ParSys {
    public String parsysPath = "D:\\GDrive\\Geo";
    private String[] rafe;
    private HashMap<Integer, HashMap<Integer,ParsysRow>> allParsys;

    public HashMap<Integer, HashMap<Integer, ParsysRow>> getAllParsys() {
        return allParsys;
    }


    public ParSys() throws Exception {
        // init
        allParsys = new HashMap<>();

        rafe = new String[7];
        rafe[1] = parsysPath + File.separator + "Parsys01.sup";
        rafe[2] = parsysPath + File.separator + "Parsys02.sup";
        rafe[3] = parsysPath + File.separator + "Parsys03.sup";
        rafe[4] = parsysPath + File.separator + "Parsys04.sup";
        rafe[5] = parsysPath + File.separator + "Parsys05.sup";
        rafe[6] = parsysPath + File.separator + "Parsys06.sup";

            for (int i = 1; i < 7; i++) {
                HashMap<Integer,ParsysRow> parsysCollection = new HashMap<>();

                try (RandomAccessFileExtended r = new RandomAccessFileExtended(rafe[i], "r")){
                    while (r.getFilePointer() != r.length()) {
                        float factor = ByteBuffer.wrap(new byte[]{r.readByte(), r.readByte(), r.readByte(), r.readByte()}).order(ByteOrder.LITTLE_ENDIAN).getFloat();
                        int num = r.readUnsignedShortLE();
                        int size = r.readUnsignedShortLE();
                        char format = (char) r.readUnsignedByte();

                        byte buf[] = new byte[11];
                        if (r.read(buf) < buf.length) throw new Exception("Can't read unit");
                        String unit = new String(buf, "cp866");

                        buf = new byte[21];
                        if (r.read(buf) < buf.length) throw new Exception("Can't read unit name");
                        String name = new String(buf, "cp866");

                        ParsysRow row = new ParsysRow(factor, num, size, format, unit, name);
                        parsysCollection.put(num, row);
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                allParsys.put(i, parsysCollection);
            }



    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < 7; i++) {
            sb.append("File: ").append(rafe[i]).append(System.lineSeparator());
            for (Map.Entry<Integer, ParsysRow> entry: allParsys.get(i).entrySet()){
                sb.append(entry.getKey()).append("\t").append(entry.getValue().toString()).append(System.lineSeparator());
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
