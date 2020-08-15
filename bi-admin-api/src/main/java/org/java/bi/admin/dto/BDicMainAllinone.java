package org.java.bi.admin.dto;


import org.java.bi.db.domain.BDicCode;
import org.java.bi.db.domain.BDicMain;

public class BDicMainAllinone {
    BDicMain dicmain;
    BDicCode[] diccodes;

    public BDicMain getDicmain() {
        return dicmain;
    }

    public void setDicmain(BDicMain dicmain) {
        this.dicmain = dicmain;
    }

    public BDicCode[] getDiccodes() {
        return diccodes;
    }

    public void setDiccodes(BDicCode[] diccodes) {
        this.diccodes = diccodes;
    }
}
