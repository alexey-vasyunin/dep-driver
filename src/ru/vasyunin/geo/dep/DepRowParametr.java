package ru.vasyunin.geo.dep;

import ru.vasyunin.geo.dep.parsys.ParSys;

public class DepRowParametr {
    public int numRef;
    public int numPar;
    public String value;

    private ParSys parSys;

    public void setParSys(ParSys parSys) {
        this.parSys = parSys;
    }

    public DepRowParametr(int numRef, int numPar, String value) {
        this.numRef = numRef;
        this.numPar = numPar;
        this.value = value;
    }

    @Override
    public String toString() {
        if (parSys != null) return "{" + parSys.getAllParsys().get(numRef).get(numPar).getName() + "=" + value +"}"; else
          return "{numRef=" + numRef + ", numPar=" + numPar + ", value="+value + "}";
    }
}
