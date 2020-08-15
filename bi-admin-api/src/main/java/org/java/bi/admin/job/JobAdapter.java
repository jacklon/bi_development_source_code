package org.java.bi.admin.job;


import java.util.List;
import java.util.Map;

/**
 * 所有的job任务必须实现此接口
 */
public interface JobAdapter {

    /**
     * 执行job任务的方法,参数由前台传入
     * @param params 执行job任务的参数
     */
    void execute(Map<String,Object> params) throws Exception;
}
