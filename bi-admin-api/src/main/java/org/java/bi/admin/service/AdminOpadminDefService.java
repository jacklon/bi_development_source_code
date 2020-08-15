package org.java.bi.admin.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.java.bi.admin.dto.OpadminDefAllinone;
import org.java.bi.admin.util.GetCurrentUser;
import org.java.bi.core.util.ResponseUtil;
import org.java.bi.db.domain.SysOpadminDef;
import org.java.bi.db.domain.SysOpadminPub;
import org.java.bi.db.service.SysOpadminDefService;
import org.java.bi.db.service.SysOpadminPubService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminOpadminDefService {
    private final Log logger = LogFactory.getLog(AdminOpadminDefService.class);

    @Autowired
    private SysOpadminDefService opadminDefService;
    @Autowired
    private SysOpadminPubService opadminPubService;


    public Object list(
            String typeCode,String typeName,String title,Integer expireFlag,
           Integer page, Integer limit, String sort) {
        List<SysOpadminDef> dicMainsList = opadminDefService.querySelective(typeCode,typeName,title,expireFlag, page, limit, sort);
        return ResponseUtil.okList(dicMainsList);
    }

    private Object validate(OpadminDefAllinone opadminDefAllinone) {
        SysOpadminDef opadminDef = opadminDefAllinone.getOpadminDef();
        if(opadminDef.getTitle()==null){
            return ResponseUtil.fail(401,"消息标题不能为空");
        }
        if(opadminDef.getContent()==null){
            return ResponseUtil.fail(401,"消息内容不能为空");
        }
        if(opadminDefAllinone.getOpadminPubs()==null){
            return ResponseUtil.fail(401,"必须设置消息要送达的客户");
        }
        return null;
    }


    @Transactional
    public Object update(OpadminDefAllinone opadminDefAllinone) {
        Object error = validate(opadminDefAllinone);
        if (error != null) {
            return error;
        }
        SysOpadminDef userinfoDef=opadminDefAllinone.getOpadminDef();
        SysOpadminPub[] userinfoPubs = opadminDefAllinone.getOpadminPubs();

        Integer mainId = userinfoDef.getId();
        if(mainId==null){
            userinfoDef.setAddBy(GetCurrentUser.getCurrentUserName());
            userinfoDef.setUpdateBy(GetCurrentUser.getCurrentUserName());
            opadminDefService.add(userinfoDef);
        }else
        {
            userinfoDef.setUpdateBy(GetCurrentUser.getCurrentUserName());
            opadminDefService.updateById(userinfoDef);
        }

        for (SysOpadminPub userinfoPub : userinfoPubs) {
            userinfoPub.setInfoId(userinfoDef.getId());
            Integer codeId=userinfoPub.getId();
            if(codeId==null){
                userinfoDef.setAddBy(GetCurrentUser.getCurrentUserName());
                userinfoDef.setUpdateBy(GetCurrentUser.getCurrentUserName());
                opadminPubService.add(userinfoPub);
            } else
            {
                userinfoPub.setUpdateBy(GetCurrentUser.getCurrentUserName());
                opadminPubService.updateById(userinfoPub);
            }
        }

        return ResponseUtil.ok();
    }

    /**
     * 删除字典表
     * @param userinfoDef
     * @return
     */
    @Transactional
    public Object delete(SysOpadminDef userinfoDef) {
        Integer userinfoDefId= userinfoDef.getId();
        if (userinfoDefId == null) {
            return ResponseUtil.badArgument();
        }

        opadminDefService.deleteById(userinfoDefId);
        opadminPubService.deleteByInfoId(userinfoDefId);
        return ResponseUtil.ok();
    }

    @Transactional
    public Object create(OpadminDefAllinone opadminDefAllinone) {
        Object error = validate(opadminDefAllinone);
        if (error != null) {
            return error;
        }

        SysOpadminDef userinfoDef = opadminDefAllinone.getOpadminDef();
        SysOpadminPub[] userinfoPubs = opadminDefAllinone.getOpadminPubs();


        //保存字典主表
        userinfoDef.setAddBy(GetCurrentUser.getCurrentUserName());
        userinfoDef.setUpdateBy(GetCurrentUser.getCurrentUserName());
        opadminDefService.add(userinfoDef);
        // 字典明细表
        for (SysOpadminPub userinfoPub : userinfoPubs) {
            userinfoPub.setInfoId(userinfoDef.getId());
            userinfoPub.setAddBy(GetCurrentUser.getCurrentUserName());
            userinfoPub.setUpdateBy(GetCurrentUser.getCurrentUserName());
            opadminPubService.add(userinfoPub);
        }
        return ResponseUtil.ok();
    }

    public Object detail(Integer id) {
        SysOpadminDef userinfoDef = opadminDefService.findById(id);
        List<SysOpadminPub> userinfoPubs = opadminPubService.queryByInfoId(id);

        Map<String, Object> data = new HashMap<>();
        data.put("userinfoDef", userinfoDef);
        data.put("userinfoPubs", userinfoPubs);

        return ResponseUtil.ok(data);
    }

    public Object getOpadminPubs(@NotNull Integer id) {
        List<SysOpadminPub> userinfoPubs=opadminPubService.queryByInfoId(id);

        return ResponseUtil.ok(userinfoPubs);
    }

}
