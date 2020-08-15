package org.java.bi.admin.dto;

import org.java.bi.db.domain.SysOpadminDef;
import org.java.bi.db.domain.SysOpadminPub;


public class OpadminDefAllinone {
    SysOpadminDef opadminDef;
    SysOpadminPub[] opadminPubs;

    public SysOpadminDef getOpadminDef() {
        return opadminDef;
    }

    public void setOpadminDef(SysOpadminDef opadminDef) {
        this.opadminDef = opadminDef;
    }

    public SysOpadminPub[] getOpadminPubs() {
        return opadminPubs;
    }

    public void setOpadminPubs(SysOpadminPub[] opadminPubs) {
        this.opadminPubs = opadminPubs;
    }
}
