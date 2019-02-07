package ru.vasyunin.geo.dep;

public class Main2 {
    public static void main(String[] args) throws DepException {
        Dep dep = new Dep("D:\\GDrive\\Geo\\Shrt_002.dep", "D:\\GDrive\\Geo\\Shrt_002.lst", "D:\\GDrive\\Geo");
        dep.open();


        dep.close();
    }
}
