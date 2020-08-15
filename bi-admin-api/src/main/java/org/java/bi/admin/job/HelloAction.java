package org.java.bi.admin.job;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("helloJob")
public class HelloAction implements JobAdapter {

    @Override
    public void execute(Map<String,Object> params) throws Exception {
        System.out.println("执行JOB任务的参数 = " + JSON.toJSONString(params));
    }

}
