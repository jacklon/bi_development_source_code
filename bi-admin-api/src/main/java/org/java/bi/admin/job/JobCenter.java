package org.java.bi.admin.job;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import org.java.bi.db.domain.SysQuartzTaskManage;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 任务调度工具类
 *
 * @author SiGuiyang
 */
@Service
public class JobCenter {



    /**
     * 根据job名称和job组名称获取触发key
     *
     * @param jobName  jobName
     * @param jobGroup jobGroup
     */
    private static TriggerKey getTriggerKey(String jobName, String jobGroup) {
        return TriggerKey.triggerKey(jobName, jobGroup);
    }

    /**
     * 获取表达式触发器
     *
     * @param scheduler 调度器
     * @param jobName   job名称
     * @param jobGroup  job组
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, String jobName, String jobGroup) {
        TriggerKey key = getTriggerKey(jobName, jobGroup);
        CronTrigger trigger = null;
        try {
            trigger = (CronTrigger) scheduler.getTrigger(key);
        } catch (SchedulerException e) {
            System.out.println("获取定时任务CronTrigger出现异常");

        }
        return trigger;
    }

    /**
     * 创建任务
     *
     * @param scheduler scheduler
     * @param job       job
     */
    public static String createJob(Scheduler scheduler, SysQuartzTaskManage job) {
        Class<? extends Job> jobClass = SyncJobFactory.class;

        // 构建job信息
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(job.getJobName(), job.getJobGroup()).build();
        // 表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
        // 按新的cron表达式构建一个新的trigger
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup())
                .withSchedule(scheduleBuilder).build();
        // 获取job触发器的名称
        jobDetail.getJobDataMap().put("JobAdapter", job);

        try {
            scheduler.scheduleJob(jobDetail, trigger);
            return "";
        } catch (SchedulerException e) {

            System.out.println("创建定时任务失败，错误信息："+ e.getMessage());
            return "创建定时任务失败，错误信息："+ e.getMessage();
        }

    }

    /**
     * 创建定时任务
     *
     * @param scheduler scheduler
     * @param job       job
     */
    public static String createJob(Scheduler scheduler, JobModel job) {

        Class<? extends Job> jobClass = SyncJobFactory.class;

        TriggerKey key = getTriggerKey(job.getJobName(), job.getJobGroup());

        try {
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(key);
            if (null == trigger) {
                // 构建job信息
                JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(job.getJobName(), job.getJobGroup())
                        .build();
                // 表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
                // 按新的cron表达式构建一个新的trigger
                trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup())
                        .withSchedule(scheduleBuilder).build();

                // 获取job触发器的名称
//                SysQuartzTaskManage sysQuartzTaskManage=new SysQuartzTaskManage();
//                BeanUtils.copyProperties(job,sysQuartzTaskManage);
//                jobDetail.getJobDataMap().put("JobAdapter", sysQuartzTaskManage);

                jobDetail.getJobDataMap().put("JobAdapter", job);
                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
                trigger = TriggerBuilder.newTrigger().withIdentity(key).withSchedule(scheduleBuilder).build();
                scheduler.rescheduleJob(key, trigger);
            }
            return "";
        } catch (SchedulerException e) {
            System.out.println("创建定时任务失败，错误信息："+ e.getMessage());
            return "创建定时任务失败，错误信息："+ e.getMessage();


        }
    }

    /**
     * 获取所有job任务
     */
    public static List<SysQuartzTaskManage> getJobs(Scheduler scheduler) {
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();

        List<SysQuartzTaskManage> jobs = Lists.newArrayList();
        try {
            Set<JobKey> jobKyes = scheduler.getJobKeys(matcher);
            for (JobKey jobKey : jobKyes) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {

                    SysQuartzTaskManage event = new SysQuartzTaskManage();
                    event.setJobName(jobKey.getName());
                    event.setJobGroup(jobKey.getGroup());
                    event.setDescription("触发器：" + trigger.getKey());
                    Trigger.TriggerState state = scheduler.getTriggerState(trigger.getKey());
                    event.setJobStatus(state.name());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        event.setCron(cronTrigger.getCronExpression());
                    }
                    jobs.add(event);
                }
            }
            System.out.println("获取所有计划中的任务列表——————————："+ JSON.toJSONString(jobs));

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobs;
    }

    /**
     * 获取正在运行的job任务
     *
     * @param scheduler scheduler
     */
    public static List<SysQuartzTaskManage> getRunningJobs(Scheduler scheduler) {

        try {
            List<JobExecutionContext> contexts = scheduler.getCurrentlyExecutingJobs();
            List<SysQuartzTaskManage> jobs = Lists.newArrayListWithCapacity(contexts.size());

            for (JobExecutionContext context : contexts) {
                SysQuartzTaskManage event = new SysQuartzTaskManage();
                JobDetail jobDetail = context.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = context.getTrigger();
                event.setJobName(jobKey.getName());
                event.setJobGroup(jobKey.getGroup());
                event.setDescription("触发器————————————{}" + trigger.getKey());
                Trigger.TriggerState state = scheduler.getTriggerState(trigger.getKey());
                event.setJobStatus(state.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    event.setCron(cronTrigger.getCronExpression());
                }
                jobs.add(event);
            }
            System.out.println("所有正在运行的job任务——————————："+ JSON.toJSONString(jobs));

            return jobs;
        } catch (SchedulerException e) {

            System.out.println("未找到运行中的job任务——————————："+ e.getMessage());
        }
        return null;
    }

    /**
     * 运行一次任务
     *
     * @param scheduler scheduler
     * @param jobName   jobName
     * @param jobGroup  jobGroup
     */
    public static String runOnce(Scheduler scheduler, String jobName, String jobGroup) {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        try {
            scheduler.triggerJob(jobKey);
            return "";
        } catch (SchedulerException e) {
            System.out.println("运行一次定时任务失败——————————："+ e.getMessage());

            return "运行一次定时任务失败——————————："+ e.getMessage();

        }
    }

    /**
     * 暂停任务
     *
     * @param scheduler scheduler
     * @param jobName   jobName
     * @param jobGroup  jobGroup
     */
    public static String  pauseJob(Scheduler scheduler, String jobName, String jobGroup) {

        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        try {
            scheduler.pauseJob(jobKey);
            return "";
        } catch (SchedulerException e) {
            System.out.println("暂停定时任务失败——————————："+ e.getMessage());

            return "暂停定时任务失败——————————："+ e.getMessage();

        }
    }

    /**
     * 恢复任务
     *
     * @param scheduler scheduler
     * @param jobName   jobName
     * @param jobGroup  jobGroup
     */
    public static String resumeJob(Scheduler scheduler, String jobName, String jobGroup) {

        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        try {
            scheduler.resumeJob(jobKey);
            return "";
        } catch (SchedulerException e) {
            System.out.println("暂停定时任务失败——————————："+ e.getMessage());
            return "暂停定时任务失败——————————："+ e.getMessage();
        }
    }

    /**
     * 获取jobKey
     *
     * @param jobName  the job name
     * @param jobGroup the job group
     * @return the job key
     */
    private  static JobKey getJobKey(String jobName, String jobGroup) {

        return JobKey.jobKey(jobName, jobGroup);
    }

    /**
     * 更新定时任务
     *
     * @param scheduler the scheduler
     */
    public static String updateJob(Scheduler scheduler, JobModel job) {
       return updateJob(scheduler, job.getJobName(), job.getJobGroup(), job.getCron(), job);
    }

    /**
     * 更新定时任务
     *
     * @param scheduler      the scheduler
     * @param jobName        the job name
     * @param jobGroup       the job group
     * @param cronExpression the cron expression

     */
    private static  String updateJob(Scheduler scheduler, String jobName, String jobGroup, String cronExpression, JobModel jobModel) {

        // 同步或异步
        Class<? extends Job> jobClass = SyncJobFactory.class;

        try {

            //修改  删除旧的 任务
//            JobKey key = new JobKey(jobName,jobGroup);
//            scheduler.deleteJob(key);

            deleteJob(scheduler,jobName,jobGroup);

            createJob(scheduler,jobModel);

            //构建job信息
//            try {
//                Class cls = Class.forName(jobModel.getClassName());
//                cls.newInstance();
//                JobDetail newJob = JobBuilder.newJob(cls).withIdentity(jobName,
//                        jobGroup)
//                        .withDescription(jobModel.getDescription()).build();
//                newJob.getJobDataMap().put("JobAdapter", jobModel);
//
//                TriggerKey triggerKey = getTriggerKey(jobName, jobGroup);
//                // 触发时间点
//                CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(jobModel.getCron().trim());
//                Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
//                        .startNow().withSchedule(cronScheduleBuilder).build();
//                //交由Scheduler安排触发
//                scheduler.scheduleJob(newJob, trigger);
//            } catch (Exception ex){
//                System.out.println("更新定时任务失败——————————："+ ex.getMessage());
//                return "更新定时任务失败——————————："+ ex.getMessage();
//            }



//            JobDetail jobDetail = scheduler.getJobDetail(getJobKey(jobName, jobGroup));
//            jobDetail = jobDetail.getJobBuilder().ofType(jobClass).build();
//
//            // 更新参数 实际测试中发现无法更新
//            JobDataMap jobDataMap = jobDetail.getJobDataMap();
//            jobDataMap.put("JobAdapter", jobModel);
//            jobDetail.getJobBuilder().usingJobData(jobDataMap);
//
//            TriggerKey triggerKey = getTriggerKey(jobName, jobGroup);
//            // 表达式调度构建器
//            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
//            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
//            // 按新的cronExpression表达式重新构建trigger
//            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
//            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
//            // 忽略状态为PAUSED的任务，解决集群环境中在其他机器设置定时任务为PAUSED状态后，集群环境启动另一台主机时定时任务全被唤醒的bug
//            if (!triggerState.name().equalsIgnoreCase("PAUSED")) {
//                // 按新的trigger重新设置job执行
//                scheduler.rescheduleJob(triggerKey, trigger);
//            }
            return "";
        } catch (Exception e) {
            System.out.println("更新定时任务失败——————————："+ e.getMessage());
            return "更新定时任务失败——————————："+ e.getMessage();
        }
    }

    /**
     * 删除定时任务
     *
     * @param scheduler scheduler
     * @param jobName   jobName
     * @param jobGroup  jobGroup
     */
    public static  String deleteJob(Scheduler scheduler, String jobName, String jobGroup) {
        try {
            TriggerKey triggerKey =  getTriggerKey(jobName,jobGroup);
            // 停止触发器
            scheduler.pauseTrigger(triggerKey);
            // 移除触发器
            scheduler.unscheduleJob(triggerKey);

            scheduler.deleteJob(getJobKey(jobName, jobGroup));
            return "";
        } catch (SchedulerException e) {
            System.out.println("删除定时任务失败——————————："+ e.getMessage());
            return "删除定时任务失败——————————："+ e.getMessage();
        }
    }

    /**
     * 通过反射执行定时任务的方法
     *
     * @param jobModel job执行的参数
     */
    public static String invoke(JobModel jobModel) {
        if (ObjectUtils.isEmpty(jobModel.getClassName())) {
            return "定时任务不能为空";
        }
        String paras= jobModel.getParameters();
        Map<String,Object> params =null;
        try {
            if (paras != null) {
                params = JSON.parseObject(paras);
            }
        } catch (Exception ex){
            return "参数格式必须为JSON数组";
        }
        String className = jobModel.getClassName();
        String methodName = jobModel.getMethodName();
        try {
            Class<?> clz = Class.forName(className);
            Object newObject = clz.newInstance();
            Method m = clz.getMethod(methodName, Map.class);
            m.invoke(newObject, params);
            return "";
        } catch (Exception ex){
            System.out.println("任务名称 = {} ————————————未启动成功，反射调用错误！！！:"+ex.getMessage());
            return "任务名称 = {} ————————————未启动成功，反射调用错误！！！:"+ex.getMessage();

        }

     }
}
