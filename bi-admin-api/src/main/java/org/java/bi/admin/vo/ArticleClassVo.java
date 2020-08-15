package org.java.bi.admin.vo;

import java.util.List;

public class ArticleClassVo {
    private Integer id;
    private String name;
    private Integer level;
    private Integer pid;
    private String pPath;
    private Integer usedRangeId;
    private String usedRangeName;
    private List<ArticleClassVo> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
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

    public List<ArticleClassVo> getChildren() {
        return children;
    }

    public void setChildren(List<ArticleClassVo> children) {
        this.children = children;
    }

    public String getpPath() {
        return pPath;
    }

    public void setpPath(String pPath) {
        this.pPath = pPath;
    }

    public Integer getUsedRangeId() {
        return usedRangeId;
    }

    public void setUsedRangeId(Integer usedRangeId) {
        this.usedRangeId = usedRangeId;
    }

    public String getUsedRangeName() {
        return usedRangeName;
    }

    public void setUsedRangeName(String usedRangeName) {
        this.usedRangeName = usedRangeName;
    }
}
