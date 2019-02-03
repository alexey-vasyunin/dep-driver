package ru.vasyunin.geo.dep.parsys;

import java.util.Arrays;

public class ParsysRow {
    private float factor;
    private int num;
    private int size;
    private char format;
    private String unit;
    private String name;

    public ParsysRow(float factor, int num, int size, char format, String unit, String name) {
        this.factor = factor;
        this.num = num;
        this.size = size;
        this.format = format;
        this.unit = unit;
        this.name = name;
    }

    public float getFactor() {
        return factor;
    }

    public int getNum() {
        return num;
    }

    public int getSize() {
        return size;
    }

    public char getFormat() {
        return format;
    }

    public String getUnit() {
        return unit;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return  String.format("%-12f %-6d %-6d %-4s %-20s %-20s", factor, num, size, format, unit, name);
    }
}
