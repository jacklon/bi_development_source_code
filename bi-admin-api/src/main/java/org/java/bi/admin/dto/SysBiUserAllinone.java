package org.java.bi.admin.dto;

import org.java.bi.db.domain.SysBiUser;
import org.java.bi.db.domain.SysUserFieldPriv;

import java.util.List;

public class SysBiUserAllinone {
    SysBiUser admin;
    List<SysUserFieldPriv> adminFieldPrivs;

    public SysBiUser getAdmin() {
        return admin;
    }

    public void setAdmin(SysBiUser admin) {
        this.admin = admin;
    }

    public List<SysUserFieldPriv> getAdminFieldPrivs() {
        return adminFieldPrivs;
    }

    public void setAdminFieldPrivs(List<SysUserFieldPriv> adminFieldPrivs) {
        this.adminFieldPrivs = adminFieldPrivs;
    }
}
