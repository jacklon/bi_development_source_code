package org.java.bi.admin.job;

import org.java.bi.admin.util.GetCurrentUser;

import org.java.bi.db.domain.SysQuartzTaskManage;
import org.java.bi.db.service.SysQuartzTaskManageService;
import org.quartz.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SyncJobFactory extends QuartzJobBean {

    @Autowired
    private SysQuartzTaskManageService sysQuartzTaskManageService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("开始执行同步任务工厂————————————");
        JobDataMap mergedJobDateMap = jobExecutionContext.getMergedJobDataMap();
        Object data= mergedJobDateMap.get("JobAdapter");
        JobModel job =new JobModel();
        BeanUtils.copyProperties(data,job);

        String jobName = job.getJobName();
        String jobGroup = job.getJobGroup();
        System.out.println("开始执行job任务：[——————————jobName: {},————jobGroup：{}————]:"+jobName+":"+jobGroup);

        String triggerKey = jobExecutionContext.getTrigger().getKey().toString();
        System.out.println("触发器执行key：[——————{}————]:"+triggerKey);

        String strError= JobCenter.invoke(job);
        if(!strError.equals("")){
            SysQuartzTaskManage sysQuartzTaskManage= sysQuartzTaskManageService.findById(job.getId().intValue());
            if(sysQuartzTaskManage!=null){
                sysQuartzTaskManage.setErrorLog(strError+"\n"+sysQuartzTaskManage.getErrorLog());
                sysQuartzTaskManage.setUpdateBy(GetCurrentUser.getCurrentUserName());
                sysQuartzTaskManageService.updateById(sysQuartzTaskManage);
            }
        }


        System.out.println("调用完成 ==================================");

    }
}
