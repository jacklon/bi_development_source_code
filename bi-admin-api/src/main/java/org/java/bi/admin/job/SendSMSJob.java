package org.java.bi.admin.job;

import com.alibaba.fastjson.JSONObject;
import org.java.bi.db.service.AliYunSendSms;
import org.java.bi.db.service.CommonDBService;

import org.java.bi.db.service.SysBiUserService;
import org.java.bi.db.service.SysSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 根据检测结果，进行短信发送
 */
@Component
public class SendSMSJob {

    @Autowired
    private CommonDBService commonDBService;

    @Autowired
    private AliYunSendSms aliYunSendSms;

    @Autowired
    private SysSetService systemConfigService;

    @Autowired
    private SysBiUserService litemallAdminService;

}
