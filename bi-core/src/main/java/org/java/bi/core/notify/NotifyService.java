package org.java.bi.core.notify;

import org.java.bi.db.domain.SysOpadminDef;
import org.java.bi.db.service.SysOpadminDefService;
import org.java.bi.db.util.NotifyPlaceholderResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 商城通知服务类
 */
public class NotifyService {
    private MailSender mailSender;

    private String sendFrom;
    private String sendTo;

    @Autowired
    private SysOpadminDefService opadminDefService;


    @Resource
    private NotifyPlaceholderResolver notifyPlaceholderResolver;

    public boolean isMailEnable() {
        return mailSender != null;
    }


    /**
     * 根据模板获取系统消息
     *
     * @param notifyType  通知类别，通过该枚举值在配置文件中获取相应的模版ID
     * @param valueMap      通知模版内容里的参数，类似"您的验证码为{1}"中{1}的值
     */

    public String notifySmsTemplate(NotifyType notifyType, Map<String, Object> valueMap) {

        String strInfoTitle="";
        if(notifyType==NotifyType.TASK_BEFORE_EXCECUTE_HINT){

            strInfoTitle="待执行任务提醒";
        } else  if(notifyType==NotifyType.TASK_DELAY_EXCECUTE_HINT){

            strInfoTitle="过期任务提醒";
        }  else if(notifyType==NotifyType.MANAGE_TASK_DELAY_HINT){

            strInfoTitle="任务监督过期提醒";
        } else if(notifyType==NotifyType.MANAGE_TASK_FIXDAY_HINT){
            strInfoTitle="固定日期过期提醒";
        } else if(notifyType==NotifyType.FLOW_WHOLE_DEPLAY_HINT){
            strInfoTitle="任务整体延期提醒";
        }
        //查询后端消息定义表
        List<SysOpadminDef> userinfoDefs= opadminDefService.querySelective(null,null,strInfoTitle,0,1,10,null);
        if(userinfoDefs.size()<=0){
            return "";
        }
        SysOpadminDef firstDef=userinfoDefs.get(0);
        String content=firstDef.getContent();
        String replaceContent= notifyPlaceholderResolver.resolveByMap(content,valueMap);
        replaceContent=replaceContent.replace("<p>","").replace("</p>","");
        return replaceContent;
    }

    /**
     * 根据模板获取系统消息
     *
     * @param notifyType  通知类别，通过该枚举值在配置文件中获取相应的模版ID
     */

    public String getStrNotifyType(NotifyType notifyType) {

        String strInfoTitle="系统消息";
        if(notifyType==NotifyType.TASK_BEFORE_EXCECUTE_HINT){

             return "待执行任务提醒";
        } else  if(notifyType==NotifyType.TASK_DELAY_EXCECUTE_HINT){

            return "过期任务提醒";
        }  else if(notifyType==NotifyType.MANAGE_TASK_DELAY_HINT){
            return "任务监督过期提醒";
        }
        return strInfoTitle;
    }



    /**
     * 邮件消息通知,
     * 接收者在spring.mail.sendto中指定
     *
     * @param subject 邮件标题
     * @param content 邮件内容
     */
    public void notifyMail(String subject, String content,String sendFrom,String sendTo) {
        if (mailSender == null)
            return;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sendFrom);
        message.setTo(sendTo);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    public MailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public SysOpadminDefService getOpadminDefService() {
        return opadminDefService;
    }

    public void setOpadminDefService(SysOpadminDefService opadminDefService) {
        this.opadminDefService = opadminDefService;
    }

    public NotifyPlaceholderResolver getNotifyPlaceholderResolver() {
        return notifyPlaceholderResolver;
    }

    public void setNotifyPlaceholderResolver(NotifyPlaceholderResolver notifyPlaceholderResolver) {
        this.notifyPlaceholderResolver = notifyPlaceholderResolver;
    }

    public String getSendFrom() {
        return sendFrom;
    }

    public void setSendFrom(String sendFrom) {
        this.sendFrom = sendFrom;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }
}
