package org.java.bi.admin.job;


import java.io.Serializable;


public class JobModel implements Serializable{


        private static final long serialVersionUID = 8984218738753141165L;
        /**
         * 主键
         */
        private Long id;


        /**
         * 任务名称
         */
        private String jobName;
        /**
         * 任务分组
         */
        private String jobGroup;
        /**
         * 任务状态<br />
         * 0：暂停<br />
         * 1：删除<br />
         * 2：正常<br />
         */
        private String jobStatus;
        /**
         * cron表达式
         */
        private String cron;
        /**
         * 描述
         */
        private String description;
        /**
         * 任务执行时调用哪个类的方法 包名+类名
         */
        private String className;
        /**
         * spring bean
         */
        private String springId;
        /**
         * 任务调用的方法名
         */
        private String methodName;

        private String parameters;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSpringId() {
        return springId;
    }

    public void setSpringId(String springId) {
        this.springId = springId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }
}
