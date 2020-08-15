package org.java.bi.core.notify;

public enum NotifyType {
    TASK_BEFORE_EXCECUTE_HINT("taskBeforeExcecuteHint"),//任务执行提前提醒
    TASK_DELAY_EXCECUTE_HINT("taskDelayExcecuteHint"),//任务执行过期提醒
    MANAGE_TASK_DELAY_HINT("manageTaskDelayHint"), //任务监督过期提醒
    MANAGE_TASK_FIXDAY_HINT("manageTaskFixDayHint"), //固定日期过期提醒
    FLOW_WHOLE_DEPLAY_HINT("flowWholeDelayHint");//任务整体延期提醒
    private String type;

    NotifyType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
