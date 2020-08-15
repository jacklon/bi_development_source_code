package org.java.bi.admin.web;

import io.swagger.annotations.Api;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.java.bi.core.util.ResponseUtil;
import org.java.bi.db.domain.SysOpadminInfo;
import org.java.bi.db.service.SysOpadminInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/admin/opadmininfo")
@Validated
@Api(description = "后台管理/消息管理/后端消息列表:/admin/opadmininfo")
public class SysOpadminInfoController {
    private final Log logger = LogFactory.getLog(SysOpadminInfoController.class);

    @Autowired
    private SysOpadminInfoService opadminInfoService;

    /**
     * 后端消息列表
     */
    @GetMapping("list")
    public Object List(
            String flowStartName,
            String flowNodeName,
            String opadminName,
            String typeCode,String typeName,String soruceCode,
            String sourceName,String title,String content,
            String strBeginDate,String strEndDate,
            Boolean ifViewed,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "update_time desc") String sort
            ){
        List<SysOpadminInfo> opadminInfoList= opadminInfoService.querySelective(flowStartName,flowNodeName,opadminName,typeCode,typeName,
                soruceCode,sourceName,title,content,strBeginDate,strEndDate,ifViewed,page,limit,sort);
        return ResponseUtil.okList(opadminInfoList);
    }

    /**
     * 后端消息列表
     */
    @GetMapping("read")
    public Object read(@NotNull Integer id){
        SysOpadminInfo opadminInfo=opadminInfoService.findById(id);
        return ResponseUtil.ok(opadminInfo);
    }


    /**
     * 后端消息列表
     */
    @GetMapping("count")
    public Object count(
            String loginUserName
    ){
        Long countRecords= opadminInfoService.countSelective(loginUserName);
        return ResponseUtil.ok(countRecords);
    }


    /**
     * 后端消息列表
     */
    @GetMapping("haveview")
    public Object haveview(Integer[]  infoIds){
        Boolean execResult= opadminInfoService.updateIfViewSelective(infoIds);
        return ResponseUtil.ok(execResult);
    }



}
