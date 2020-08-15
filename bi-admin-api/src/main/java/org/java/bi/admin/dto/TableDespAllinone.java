package org.java.bi.admin.dto;


import org.java.bi.db.domain.SysTableDesp;
import org.java.bi.db.domain.SysTableFieldDesp;

public class TableDespAllinone {
    SysTableDesp tableDesp;
    SysTableFieldDesp[] fieldsDesp;

    public SysTableDesp getTableDesp() {
        return tableDesp;
    }

    public void setTableDesp(SysTableDesp tableDesp) {
        this.tableDesp = tableDesp;
    }

    public SysTableFieldDesp[] getFieldsDesp() {
        return fieldsDesp;
    }

    public void setFieldsDesp(SysTableFieldDesp[] fieldsDesp) {
        this.fieldsDesp = fieldsDesp;
    }
}
