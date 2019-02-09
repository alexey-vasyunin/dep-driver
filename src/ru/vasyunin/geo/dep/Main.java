package ru.vasyunin.geo.dep;

public class Main {
    public static void main(String[] args) throws DepException {
        Dep dep = new Dep("D:\\GDrive\\Geo\\Shrt_002.dep", "D:\\GDrive\\Geo\\Shrt_002.lst", "D:\\GDrive\\Geo");
        dep.open();

        for (DepRowStruct drs : dep){
            System.out.println(drs);
        }

        dep.close();
    }
}
