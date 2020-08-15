package org.java.bi.admin.job;

public interface JobConstants {


    interface Operation {
        String all = "all";
        String info = "info";
        String delete = "delete";
        String save = "save";
        String update = "update";
        String execute = "execute";
        String pause = "pause";
        String resume = "resume";
    }

    enum JobStatus {
        pause("暂停", "暂停"),
        delete("未启动", "未启动"),
        running("运行", "运行");
        public String status;

        public String name;


        JobStatus(String status, String name) {
            this.status = status;
            this.name = name;
        }
    }
}
