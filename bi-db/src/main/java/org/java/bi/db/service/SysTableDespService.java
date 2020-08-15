package org.java.bi.db.service;

import com.github.pagehelper.PageHelper;
import org.java.bi.db.dao.SysTableDespMapper;
import org.java.bi.db.dao.SysTableFieldDespMapper;
import org.java.bi.db.domain.SysTableDesp;
import org.java.bi.db.domain.SysTableDespExample;
import org.java.bi.db.domain.SysTableFieldDesp;
import org.java.bi.db.domain.SysTableFieldDespExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysTableDespService {
    @Resource
    private SysTableDespMapper sysTableDespMapper;

    @Resource
    private SysTableFieldDespMapper sysTableFieldDespMapper;

    @Autowired
    private CommonDBService commonDBService;

    public List<SysTableDesp> queryAll(){
        SysTableDespExample example=new SysTableDespExample();
        SysTableDespExample.Criteria criteria=example.createCriteria();

        criteria.andDeletedEqualTo(false);
        return sysTableDespMapper.selectByExample(example);
    }

    public List<SysTableDesp> querySelective(String oltTableDesp,
        String olfFieldName,String olfFieldDesp,Integer page,Integer limit,String sort){
        SysTableDespExample example=new SysTableDespExample();
        SysTableDespExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(oltTableDesp)){
            criteria.andOltTableDespLike("%"+oltTableDesp+"%");
        }
        if(!StringUtils.isEmpty(olfFieldName)||!StringUtils.isEmpty(olfFieldDesp)){
            SysTableFieldDespExample fieldExample=new SysTableFieldDespExample();
            SysTableFieldDespExample.Criteria fieldCriteria=fieldExample.createCriteria();
            if(!StringUtils.isEmpty(olfFieldName)){
                fieldCriteria.andOlfFieldNameEqualTo(olfFieldName);
            }
            if(!StringUtils.isEmpty(olfFieldDesp)){
                fieldCriteria.andOlfFieldDespLike("%"+olfFieldDesp+"%");
            }
            criteria.andDeletedEqualTo(false);
            List<SysTableFieldDesp> sysTableFieldDespList= sysTableFieldDespMapper.selectByExample(fieldExample);
            List<Integer> mainIdList=sysTableFieldDespList.stream().map(SysTableFieldDesp::getOltId).distinct().collect(Collectors.toList());
            if(mainIdList!=null&&mainIdList.size()>0){
                criteria.andIdIn(mainIdList);
            }else
            {
                return null;
            }
        }

        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(sort)){
            example.setOrderByClause(sort);
        }
        PageHelper.startPage(page,limit);
        return sysTableDespMapper.selectByExample(example);
    }


    public int updateById(SysTableDesp homeIcon){
        homeIcon.setUpdateTime(LocalDateTime.now());
        return sysTableDespMapper.updateByPrimaryKeySelective(homeIcon);
    }

    public void deleteById(Integer id){
        sysTableDespMapper.deleteByPrimaryKey(id);
//        sysTableDespMapper.logicalDeleteByPrimaryKey(id);
    }


    public void add(SysTableDesp homeIcon){
        homeIcon.setAddTime(LocalDateTime.now());
        homeIcon.setUpdateTime(LocalDateTime.now());
        sysTableDespMapper.insertSelective(homeIcon);
    }


    public SysTableDesp findById(Integer id){
        return sysTableDespMapper.selectByPrimaryKey(id);
    }

    public SysTableDesp findByIdOrDesp(Integer id,String desp) {
        SysTableDespExample example = new SysTableDespExample();
        SysTableDespExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(id)){
            criteria.andIdEqualTo(id);
        } else
        {
            if(!StringUtils.isEmpty(desp)){
                criteria.andOltTableDespEqualTo(desp);
            }
        }
        criteria.andDeletedEqualTo(false);

        return sysTableDespMapper.selectOneByExample(example);
    }

    /**
     * 根据表的外键获取所有有关联表及删除语句
     * @param result
     * @param userName
     * @param parentOltId
     * @param parentDataId
     */

    public void getCascadeDeleteSqlByOltParentId(List result,String userName,Integer parentOltId,Integer parentDataId,Integer deleted) {
        SysTableDespExample example = new SysTableDespExample();
        SysTableDespExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(parentOltId)){
            criteria.andOltParentIdEqualTo(parentOltId);
        }
        criteria.andDeletedEqualTo(false);
        //进行递归处理
        List<SysTableDesp> sysTableDespList= sysTableDespMapper.selectByExample(example);

        for (SysTableDesp sysTableDesp : sysTableDespList) {
            String strSql = "update " + sysTableDesp.getOltDbTalbeViewName() + " set deleted="+deleted+",update_time=now(),update_by='" +
                    userName + "' where " + sysTableDesp.getOltForeignFieldName() + "=" + parentDataId;
            result.add(strSql);


            String strSelectSql = "select id from " + sysTableDesp.getOltDbTalbeViewName() +
                    " where " + sysTableDesp.getOltForeignFieldName() + "=" + parentDataId;
            Map<String, Object> param = new HashMap<>();
            param.put("sqlS", strSelectSql);
            List dataList = commonDBService.procedureDaoList(param);

            if (dataList != null && dataList.size() > 0) {
                for (int ii = 0; ii < dataList.size(); ii++) {
                    Map<String, Integer> mapArray = (Map<String, Integer>) dataList.get(ii);
                    Integer dataId=mapArray.get("id");
                    getCascadeDeleteSqlByOltParentId(result, userName, sysTableDesp.getId(), dataId,deleted);
                }
            }
        }
    }


    public boolean checkExistByName(String name) {
        SysTableDespExample example = new SysTableDespExample();
        example.or().andOltTableDespEqualTo(name).andDeletedEqualTo(false);
        return sysTableDespMapper.countByExample(example) != 0;
    }
}
