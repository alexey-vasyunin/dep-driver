package ru.vasyunin.geo.dep;

import lombok.Data;
import ru.vasyunin.geo.dep.parsys.ParSys;

@Data
public class DepRowParametr {
    private int numRef;
    private int numPar;
    private String value;

    private ParSys parSys;

    /**
     *
     * @param numRef Number of parsys file
     * @param numPar Number of parametr
     * @param value Value of parametr
     */
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
