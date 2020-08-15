package org.java.bi.admin.vo;

import java.util.List;

public class BCorpInfoVo {
    private Integer id;
    private String name;
    private Integer level;
    private Integer pid;

    private Integer citId;
    private String citName;

    private Integer depTypeId;
    private String depTypeName;

    private String chargePerson;
    private String cwCode;

    private Integer flowDataCount;


    private List<BCorpInfoVo> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public List<BCorpInfoVo> getChildren() {
        return children;
    }

    public void setChildren(List<BCorpInfoVo> children) {
        this.children = children;
    }

    public Integer getCitId() {
        return citId;
    }

    public void setCitId(Integer citId) {
        this.citId = citId;
    }

    public String getCitName() {
        return citName;
    }

    public void setCitName(String citName) {
        this.citName = citName;
    }

    public Integer getDepTypeId() {
        return depTypeId;
    }

    public void setDepTypeId(Integer depTypeId) {
        this.depTypeId = depTypeId;
    }

    public String getDepTypeName() {
        return depTypeName;
    }

    public void setDepTypeName(String depTypeName) {
        this.depTypeName = depTypeName;
    }

    public String getChargePerson() {
        return chargePerson;
    }

    public void setChargePerson(String chargePerson) {
        this.chargePerson = chargePerson;
    }

    public String getCwCode() {
        return cwCode;
    }

    public void setCwCode(String cwCode) {
        this.cwCode = cwCode;
    }

    public Integer getFlowDataCount() {
        return flowDataCount;
    }

    public void setFlowDataCount(Integer flowDataCount) {
        this.flowDataCount = flowDataCount;
    }
}
