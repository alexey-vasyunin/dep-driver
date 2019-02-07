package ru.vasyunin.geo.dep;

import ru.vasyunin.geo.dep.parsys.ParSys;
import ru.vasyunin.geo.dep.parsys.ParsysRow;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class DepRowStruct {

    private long numRec;
    private int uniqueWell;
    private int recLenght;
    private int keyField;
    private int numAllPars;
    private DepRowParametr[] params;
    private ParSys parSys;


    public DepRowStruct(ParSys p) throws DepException{
        parSys = p;
    }

    public long setRow(byte[] buffer) throws DepException{
        try (ByteArrayInputStreamExtended dis = new ByteArrayInputStreamExtended(buffer)) {
            numRec = dis.readUnsignedIntLE();
            uniqueWell = dis.readUnsignedShortLE();
            recLenght = dis.readUnsignedShortLE();
            keyField = dis.readUnsignedByteLE();
            numAllPars = dis.readUnsignedByteLE();

            params = new DepRowParametr[numAllPars];
            for (int i = 0; i < numAllPars; i++) {
                int numRef = dis.readUnsignedByteLE();
                int numPar = dis.readUnsignedByteLE();
                ParsysRow parametr = parSys.getAllParsys().get(numRef).get(numPar);

                byte[] b = new byte[parametr.getSize()];
                if (dis.read(b) < b.length) throw new DepException("Can't read parametr");

                String val = "";
                switch (parametr.getFormat()){
                    case 'f':  val = String.valueOf(ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getFloat()); break;
                    case 'i': val = String.valueOf(ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getShort()); break;
                    case 'l': val = String.valueOf(ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getInt()); break;
                    case 'c': val = new String(b, "cp866");; break;
                    default: val = Arrays.toString(b) + ":::" + parametr.getFormat() + ":::" + parametr.getSize(); break;
                }

                params[i] = new DepRowParametr(numRef, numPar, val);
                params[i].setParSys(parSys);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return numRec;
    }

    @Override
    public String toString() {
        return  String.format("%-8d %-8d %-6d %-4d %-4d %-120s", numRec, uniqueWell, recLenght, keyField, numAllPars, Arrays.toString(params));
    }
}
