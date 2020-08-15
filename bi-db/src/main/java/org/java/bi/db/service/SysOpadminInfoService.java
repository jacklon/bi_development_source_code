package org.java.bi.db.service;

import com.github.pagehelper.PageHelper;
import org.java.bi.db.dao.SysOpadminInfoMapper;
import org.java.bi.db.domain.SysOpadminDef;
import org.java.bi.db.domain.SysOpadminInfo;
import org.java.bi.db.domain.SysOpadminInfoExample;
import org.java.bi.db.domain.SysOpadminPub;
import org.java.bi.db.util.DateUtil;
import org.java.bi.db.util.NotifyPlaceholderResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class SysOpadminInfoService {
    @Resource
    private SysOpadminInfoMapper litemallOpadminInfoMapper;

    @Resource
    private NotifyPlaceholderResolver notifyPlaceholderResolver;

    @Autowired
    private SysOpadminDefService opadminDefService;

    @Autowired
    private SysOpadminPubService opadminPubService;

    public List<SysOpadminInfo> querySelective(
            String flowStartName,
            String flowNodeName,
            String opadminName,
            String typeCode,String typeName,String sourceCode,String sourceName,String title,String content,
            String strBeginDate,String strEndDate,Boolean ifViewed,
            Integer page,Integer limit,String sort){
        SysOpadminInfoExample example=new SysOpadminInfoExample();
        SysOpadminInfoExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(flowStartName)){
            criteria.andFlowStartNameLike("%"+flowStartName+"%");
        }
        if(!StringUtils.isEmpty(flowNodeName)){
            criteria.andFlowNodeNameLike("%"+flowNodeName+"%");
        }
        if(!StringUtils.isEmpty(opadminName)){
            criteria.andOpadminNameEqualTo(opadminName);
        }
        if(!StringUtils.isEmpty(typeCode)){
            criteria.andTypeCodeEqualTo(typeCode);
        }
        if(!StringUtils.isEmpty(typeName)){
            criteria.andTypeNameEqualTo(typeName);
        }
        if(!StringUtils.isEmpty(sourceCode)){
            criteria.andSourceCodeEqualTo(sourceCode);
        }
        if(!StringUtils.isEmpty(sourceName)){
            criteria.andSourceNameEqualTo(sourceName);
        }
        if(!StringUtils.isEmpty(title)){
            criteria.andTitleLike("%"+title+"%");
        }
        if(!StringUtils.isEmpty(content)){
            criteria.andContentLike("%"+content+"%");
        }
        if(!StringUtils.isEmpty(strBeginDate)){
            criteria.andAddTimeGreaterThanOrEqualTo(DateUtil.getLocalDateTimeFromStringHM(strBeginDate));
        }
        if(!StringUtils.isEmpty(strEndDate)){
            criteria.andAddTimeLessThanOrEqualTo(DateUtil.getLocalDateTimeFromStringHM(strEndDate));
        }
        if(!StringUtils.isEmpty(ifViewed)){
            criteria.andIfViewedEqualTo(ifViewed);
        }
        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(sort)){
            example.setOrderByClause(sort);
        }
        PageHelper.startPage(page,limit);
        return litemallOpadminInfoMapper.selectByExample(example);
    }

    public void add(SysOpadminInfo userinfoDef){
        userinfoDef.setAddTime(LocalDateTime.now());
        userinfoDef.setUpdateTime(LocalDateTime.now());
        litemallOpadminInfoMapper.insertSelective(userinfoDef);
    }

    public int updateById(SysOpadminInfo userinfoDef){
        userinfoDef.setUpdateTime(LocalDateTime.now());
        return litemallOpadminInfoMapper.updateByPrimaryKeySelective(userinfoDef);
    }
    public void deleteById(Integer id){
        litemallOpadminInfoMapper.logicalDeleteByPrimaryKey(id);
    }


    public SysOpadminInfo findById(Integer id){
        return litemallOpadminInfoMapper.selectByPrimaryKey(id);
    }

    public boolean checkExistByName(String title) {
        SysOpadminInfoExample example = new SysOpadminInfoExample();
        example.or().andTitleEqualTo(title).andDeletedEqualTo(false);
        return litemallOpadminInfoMapper.countByExample(example) != 0;
    }

    public Boolean addSysOpadminInfo(SysOpadminInfo litemallOpadminInfo,
        Map<String,Object> mapParas){
        //查询后端消息定义表
        String typeName=litemallOpadminInfo.getTypeName();
        String infoTitle=litemallOpadminInfo.getTitle();
        List<SysOpadminDef> opadminDefs= opadminDefService.querySelective(null,typeName,infoTitle,0,1,10,null);
        if(opadminDefs.size()<=0){
            return false;
        }
        SysOpadminDef firstDef=opadminDefs.get(0);
        litemallOpadminInfo.setInfoId(firstDef.getId());
        litemallOpadminInfo.setTypeCode(firstDef.getTypeCode());
        litemallOpadminInfo.setWebHint(firstDef.getWebHint());
        litemallOpadminInfo.setSmsHint(firstDef.getSmsHint());
        litemallOpadminInfo.setMailHint(firstDef.getMailHint());
        litemallOpadminInfo.setPopHint(firstDef.getPopHint());

        String content=firstDef.getContent();
        String replaceContent= notifyPlaceholderResolver.resolveByMap(content,mapParas);
        litemallOpadminInfo.setContent(replaceContent);
        List<SysOpadminPub> opadminPubs= opadminPubService.queryByInfoId(firstDef.getId());
        //按通知对象进行写入
        for(SysOpadminPub opadminPub:opadminPubs){
            SysOpadminInfo newOpadminInfo=litemallOpadminInfo;
            newOpadminInfo.setOpadminId(opadminPub.getUserId());
            newOpadminInfo.setOpadminName(opadminPub.getUserName());

            add(newOpadminInfo);
        }
        return true;
    }

    public Long countSelective(
            String opadminName){
        SysOpadminInfoExample example=new SysOpadminInfoExample();
        SysOpadminInfoExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(opadminName)){
            criteria.andOpadminNameEqualTo(opadminName);
        }

        criteria.andDeletedEqualTo(false);
        criteria.andIfViewedEqualTo(false);
        example.orderBy("add_time desc");

        return litemallOpadminInfoMapper.countByExample(example);
    }

    public Boolean updateIfViewSelective(
            Integer[]  infoIds){
        SysOpadminInfoExample example=new SysOpadminInfoExample();
        SysOpadminInfoExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(infoIds)){
            criteria.andIdIn(Arrays.asList(infoIds));
        }

        List<SysOpadminInfo> opadminInfos=litemallOpadminInfoMapper.selectByExample(example);

        for(SysOpadminInfo opadminInfo:opadminInfos){
            opadminInfo.setIfViewed(true);
            litemallOpadminInfoMapper.updateByPrimaryKey(opadminInfo);
        }

        return true;
    }

}
