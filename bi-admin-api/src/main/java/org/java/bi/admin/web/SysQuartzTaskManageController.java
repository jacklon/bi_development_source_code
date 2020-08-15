package org.java.bi.admin.web;

import io.swagger.annotations.Api;
import org.java.bi.admin.job.JobCenter;
import org.java.bi.admin.job.JobConstants;
import org.java.bi.admin.job.JobModel;

import org.java.bi.admin.util.GetCurrentUser;
import org.java.bi.core.util.ResponseUtil;

import org.java.bi.db.domain.SysQuartzTaskManage;
import org.java.bi.db.service.SysQuartzTaskManageService;
import org.quartz.Scheduler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin/job")
@Validated
@Api(description = "系统管理/数据操作日志:/admin/sysOperationLog")
public class SysQuartzTaskManageController {
    @Autowired
    private SysQuartzTaskManageService jobService;

    @Autowired
    private Scheduler scheduler;


    @GetMapping("/list")
    public Object queryAllJobs(
            String jobName,String jobGroup,String jobStatus,String className,
            String parameters,String methodName,String description,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "update_time desc") String sort) {
        List<SysQuartzTaskManage> result= jobService.querySelective(jobName,jobGroup,jobStatus,className,
                parameters,methodName,description,page,limit,sort);
        return ResponseUtil.okList(result);
    }

    @PostMapping("/update")
    public Object update(@RequestBody SysQuartzTaskManage sysQuartzTaskManage) {
        String strError=valid(sysQuartzTaskManage);
        if(!strError.equals("")){
            return ResponseUtil.fail(502,strError);
        }
        if(jobService.checkExist(sysQuartzTaskManage)){
            return ResponseUtil.fail(502,"任务名称已经存在，请修改任务名");
        }
        jobService.updateById(sysQuartzTaskManage);
        if(!sysQuartzTaskManage.getJobStatus().equals("未启动")) {
           JobModel jobModel=new JobModel();
           BeanUtils.copyProperties(sysQuartzTaskManage,jobModel);
           String returnError= publish(sysQuartzTaskManage,jobModel, JobConstants.Operation.update);
           if(!returnError.equals("")) {
               return ResponseUtil.fail(502, returnError);
           }
        }
        return ResponseUtil.ok(sysQuartzTaskManage);
    }

    @PostMapping("/create")
    public Object saveJob(@RequestBody SysQuartzTaskManage sysQuartzTaskManage) {
        String strError=valid(sysQuartzTaskManage);
        if(!strError.equals("")){
            return ResponseUtil.fail(502,strError);
        }
        if(jobService.checkExist(sysQuartzTaskManage)){
            return ResponseUtil.fail(502,"任务名称已经存在，请修改任务名");
        }
        sysQuartzTaskManage.setAddBy(GetCurrentUser.getCurrentUserName());
        sysQuartzTaskManage.setUpdateBy(GetCurrentUser.getCurrentUserName());
        jobService.add(sysQuartzTaskManage);
        //创建后不立即启动任务
//        publish(jobModel,JobConstants.Operation.save);
        return ResponseUtil.ok(sysQuartzTaskManage);
    }

    @PostMapping("/start")
    public Object startJob(@RequestBody SysQuartzTaskManage sysQuartzTaskManage) {
        //启动任务
       JobModel jobModel=new JobModel();
       BeanUtils.copyProperties(sysQuartzTaskManage,jobModel);
       jobModel.setId((long)sysQuartzTaskManage.getId());
       String strError=  publish(sysQuartzTaskManage,jobModel,JobConstants.Operation.save);
        if(!strError.equals("")) {
            return ResponseUtil.fail(502, strError);
        }
        sysQuartzTaskManage.setJobStatus("运行");

        jobService.updateById(sysQuartzTaskManage);

        return ResponseUtil.ok(sysQuartzTaskManage);
    }

    @GetMapping("/delete")
    public Object deleteJob(Integer id) {
        SysQuartzTaskManage sysQuartzTaskManage = jobService.findById(id);
        JobModel jobModel= new JobModel();
        BeanUtils.copyProperties(sysQuartzTaskManage,jobModel);
        if(!jobModel.getJobStatus().equals("未启动")) {
            String strError=publish(sysQuartzTaskManage,jobModel, JobConstants.Operation.delete);
            if(!strError.equals("")) {
                return ResponseUtil.fail(502, strError);
            }
        }
        jobService.deleteById(id);
        return ResponseUtil.ok();
    }

    @GetMapping("/execute")
    public Object doJob(Integer id) {
        SysQuartzTaskManage sysQuartzTaskManage = jobService.findById(id);
        JobModel jobModel= new JobModel();
        BeanUtils.copyProperties(sysQuartzTaskManage,jobModel);
        String strError=publish(sysQuartzTaskManage,jobModel,JobConstants.Operation.execute);
        if(!strError.equals("")) {
            return ResponseUtil.fail(502, strError);
        }
        sysQuartzTaskManage.setJobStatus("运行");
        jobService.updateById(sysQuartzTaskManage);
        return ResponseUtil.ok();

    }

    @GetMapping("/resume")
    public Object resume(Integer id) {
        SysQuartzTaskManage sysQuartzTaskManage = jobService.findById(id);
        JobModel jobModel= new JobModel();
        BeanUtils.copyProperties(sysQuartzTaskManage,jobModel);
        String strError=publish(sysQuartzTaskManage,jobModel,JobConstants.Operation.resume);
        if(!strError.equals("")) {
            return ResponseUtil.fail(502, strError);
        }
        sysQuartzTaskManage.setJobStatus("运行");
        jobService.updateById(sysQuartzTaskManage);
        return ResponseUtil.ok();

    }

    @GetMapping("/pause")
    public Object pause(Integer id) {
        SysQuartzTaskManage sysQuartzTaskManage = jobService.findById(id);
        JobModel jobModel= new JobModel();
        BeanUtils.copyProperties(sysQuartzTaskManage,jobModel);
        String strError=publish(sysQuartzTaskManage,jobModel,JobConstants.Operation.pause);
        if(!strError.equals("")) {
            return ResponseUtil.fail(502, strError);
        }
        sysQuartzTaskManage.setJobStatus("暂停");
        jobService.updateById(sysQuartzTaskManage);
        return ResponseUtil.ok();

    }

    @GetMapping("/detail")
    public Object queryJob(Integer id) {
        SysQuartzTaskManage sysQuartzTaskManage = jobService.findById(id);
        return ResponseUtil.ok(sysQuartzTaskManage);
    }


    private String  valid(SysQuartzTaskManage sysQuartzTaskManage){
        String strError="";
        if(sysQuartzTaskManage.getJobName()==null){
            strError="定时任务名称不能为空";
        }
        if(sysQuartzTaskManage.getCron()==null){
            strError="定时器计划不能为空";
        }
        return strError;
    }


    // 立即执行，更新，删除，恢复，暂停使用redis队列实现
    private String publish(SysQuartzTaskManage sysQuartzTaskManage,JobModel jobModel, String operation) {
        // 立即执行
        String strError="";
        if (JobConstants.Operation.execute.equals(operation)) {
            System.out.println("触发立即执行 ");
            jobModel.setJobStatus(JobConstants.JobStatus.running.status);
            strError= JobCenter.invoke(jobModel);
            jobService.updateById(sysQuartzTaskManage);
        } else if (JobConstants.Operation.pause.equals(operation)) { // 暂停
            System.out.println("触发暂停 ");
            jobModel.setJobStatus(JobConstants.JobStatus.pause.status);
            strError=JobCenter.pauseJob(scheduler, jobModel.getJobName(), jobModel.getJobGroup());
            jobService.updateById(sysQuartzTaskManage);
        } else if (JobConstants.Operation.resume.equals(operation)) { // 恢复
            System.out.println("触发恢复 ");
            jobModel.setJobStatus(JobConstants.JobStatus.running.status);
            strError=JobCenter.resumeJob(scheduler, jobModel.getJobName(), jobModel.getJobGroup());
            jobService.updateById(sysQuartzTaskManage);
        } else if (JobConstants.Operation.delete.equals(operation)) { // 删除
            System.out.println("触发删除 ");
            strError=JobCenter.deleteJob(scheduler, jobModel.getJobName(), jobModel.getJobGroup());
            jobModel.setJobStatus(JobConstants.JobStatus.delete.status);
            jobService.updateById(sysQuartzTaskManage);
        } else if (JobConstants.Operation.save.equals(operation)) { // 创建新的job任务
            System.out.println("触发创建新的job任务 ");
            strError=  JobCenter.createJob(scheduler, jobModel);
        } else if (JobConstants.Operation.update.equals(operation)) { // 更新job任务
            System.out.println("触发更新job任务 ");
            strError=JobCenter.updateJob(scheduler, jobModel);
        }
        return strError;
    }
}
