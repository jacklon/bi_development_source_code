package org.java.bi.db.service;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Created by lizeng on 2017/1/13.
 */
@Service
public class AliYunSendSms {

    @Autowired
    private SysSetService systemConfigService;
    @Autowired
    private SysOpadminInfoService opadminInfoService;
    @Autowired
    private SysBiUserService adminService;

    @Autowired
    private CommonDBService commonDBService;

//    private static String AccessKeyId = "LTAIaHQJdRS5ZzDr";//对应的accesskeyid
//    private static String AccessKeySecret = "DkcmcE5rcutEf6sWVFzdQcSSYCp3t3";//对应的accesskeysecret
//    private static String templateCode="SMS_193522026"

    public  enum SmsConfig{
        placeholder("",""),
        register("SMS_14265297","注册验证"),
        forgetPassword("SMS_14265295","变更验证"),
        login("SMS_14265299","登录验证"),
        bindPhone("SMS_14265301","身份验证"),
        huodong("SMS_90965071","活动验证");
        private String template;
        private String sign;

        private SmsConfig(String template, String sign) {
            this.template = template;
            this.sign = sign;
        }

        public String getTemplate() {
            return template;
        }

        public String getSign() {
            return sign;
        }
    }


}
