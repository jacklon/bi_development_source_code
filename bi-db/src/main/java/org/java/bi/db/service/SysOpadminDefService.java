package org.java.bi.db.service;

import com.github.pagehelper.PageHelper;
import org.java.bi.db.dao.SysOpadminDefMapper;
import org.java.bi.db.domain.SysOpadminDef;
import org.java.bi.db.domain.SysOpadminDefExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SysOpadminDefService {
    @Resource
    private SysOpadminDefMapper litemallOpadminDefMapper;

    public List<SysOpadminDef> querySelective(
            String typeCode,String typeName, String title,Integer expireFlag,
            Integer page,Integer limit,String sort){
        SysOpadminDefExample example=new SysOpadminDefExample();
        SysOpadminDefExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(typeCode)){
            criteria.andTypeCodeEqualTo(typeCode);
        }
        if(!StringUtils.isEmpty(typeName)){
            criteria.andTypeNameEqualTo(typeName);
        }
        if(!StringUtils.isEmpty(title)){
            criteria.andTitleLike("%"+title+"%");
        }
        if(!StringUtils.isEmpty(expireFlag)){
            if(expireFlag==0) {
                criteria.andExpireFlagEqualTo(false);
            } else  if(expireFlag==1) {
                criteria.andExpireFlagEqualTo(true);
            }
        }

        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(sort)){
            example.setOrderByClause(sort);
        }
        PageHelper.startPage(page,limit);
        return litemallOpadminDefMapper.selectByExample(example);
    }

    public void add(SysOpadminDef opadminDef){
        opadminDef.setAddTime(LocalDateTime.now());
        opadminDef.setUpdateTime(LocalDateTime.now());
        litemallOpadminDefMapper.insertSelective(opadminDef);
    }

    public int updateById(SysOpadminDef opadminDef){
        opadminDef.setUpdateTime(LocalDateTime.now());
        return litemallOpadminDefMapper.updateByPrimaryKeySelective(opadminDef);
    }
    public void deleteById(Integer id){
        litemallOpadminDefMapper.logicalDeleteByPrimaryKey(id);
    }


    public SysOpadminDef findById(Integer id){
        return litemallOpadminDefMapper.selectByPrimaryKey(id);
    }

    public boolean checkExistByName(String typeName, String title) {
        SysOpadminDefExample example = new SysOpadminDefExample();
        example.or().andTypeNameEqualTo(typeName).andTitleEqualTo(title).andDeletedEqualTo(false);
        return litemallOpadminDefMapper.countByExample(example) != 0;
    }
}
