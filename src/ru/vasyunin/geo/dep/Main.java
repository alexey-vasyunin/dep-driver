package ru.vasyunin.geo.dep;

import ru.vasyunin.geo.dep.*;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static String exampleShrt = "D:\\GDrive\\Geo\\Shrt_002.dep";
    public static String exampleTime = "D:\\GDrive\\Geo\\Time_017.dep";

    public static void main(String[] args) throws Exception {

        try (RandomAccessFileExtended raf = new RandomAccessFileExtended(Main.exampleShrt, "r")){
            raf.seek(0);
            byte[] buffer = new byte[8192];
            DepRowStruct row = new DepRowStruct();
            for (int i = 0; i < 100; i++) {
                long currentOffset = raf.getFilePointer();
                raf.seek(currentOffset + 6);
                int reclength = raf.readUnsignedShortLE();
                raf.seek(currentOffset);
                if (raf.read(buffer, 0, reclength) < reclength) {
                    throw new Exception("Can't read record");
                }
                row.setRow(buffer);

                System.out.println(row);
                raf.seek(currentOffset + reclength);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



//    private parseRow(){
//
//    }
}
