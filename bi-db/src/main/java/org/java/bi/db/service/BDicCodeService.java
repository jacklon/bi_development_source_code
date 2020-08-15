package org.java.bi.db.service;

import com.github.pagehelper.PageHelper;
import org.java.bi.db.dao.BDicCodeMapper;

import org.java.bi.db.domain.BDicCode;
import org.java.bi.db.domain.BDicCodeExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BDicCodeService {
    @Resource
    private BDicCodeMapper litemallDicCodeMapper;

    @Autowired
    private CommonDBService commonDBService;

    public List<BDicCode> queryIndex(){
        BDicCodeExample example=new BDicCodeExample();
        example.or().andDeletedEqualTo(false).andEnabledEqualTo(true);
        return litemallDicCodeMapper.selectByExample(example);
    }

    /**
     * 模糊查找
     * @param name
     * @param page
     * @param limit
     * @param sort
     * @return
     */
    public List<BDicCode> querySelective(String name, Integer page,Integer limit,String sort){
        BDicCodeExample example=new BDicCodeExample();
        BDicCodeExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%"+name+"%");
        }
        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(sort)){
            example.setOrderByClause(sort);
        } else{
            example.setOrderByClause("ordernumber");
        }
        PageHelper.startPage(page,limit);
        return litemallDicCodeMapper.selectByExample(example);
    }

    /**
     * 精确查找
     * @param name
     * @param sort
     * @return
     */
    public List<BDicCode> findByDicmainIdOrDicmainName(Integer dicmainId, String name,String sort){
        BDicCodeExample example=new BDicCodeExample();
        BDicCodeExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(dicmainId)){
            criteria.andMainidEqualTo(dicmainId);
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andMainnameEqualTo(name);
        }
        criteria.andDeletedEqualTo(false);
        example.setOrderByClause("ordernumber");

//        if(!StringUtils.isEmpty(sort)){
//            example.setOrderByClause(sort);
//        }else
//        {
//            example.setOrderByClause("ordernumber");
//        }

        return litemallDicCodeMapper.selectByExample(example);
    }

    public int updateById(BDicCode litemallDicCode){
        litemallDicCode.setUpdateTime(LocalDateTime.now());
        return litemallDicCodeMapper.updateByPrimaryKeySelective(litemallDicCode);
    }

    public void deleteById(Integer id){
        litemallDicCodeMapper.logicalDeleteByPrimaryKey(id);
    }

    public void deleteByMainId(Integer mainId){
        BDicCodeExample example = new BDicCodeExample();
        example.or().andMainidEqualTo(mainId);
        litemallDicCodeMapper.logicalDeleteByExample(example);
    }


    public void add(BDicCode litemallDicCode){
        litemallDicCode.setAddTime(LocalDateTime.now());
        litemallDicCode.setUpdateTime(LocalDateTime.now());
        litemallDicCodeMapper.insertSelective(litemallDicCode);
    }


    public BDicCode findById(Integer id){
        return litemallDicCodeMapper.selectByPrimaryKey(id);
    }

    public List<BDicCode> queryByMainId(Integer gid) {
        BDicCodeExample example = new BDicCodeExample();
        example.or().andMainidEqualTo(gid).andDeletedEqualTo(false);
        example.setOrderByClause("ordernumber");
        return litemallDicCodeMapper.selectByExample(example);
    }

    public List<BDicCode> queryByMainIdList(List<Integer> mainIdList) {
        BDicCodeExample example = new BDicCodeExample();
        example.or().andMainidIn(mainIdList).andDeletedEqualTo(false);
        example.setOrderByClause("mainid,ordernumber");
        return litemallDicCodeMapper.selectByExample(example);
    }

    public List<BDicCode> queryByIdList(List<Integer> idList) {
        BDicCodeExample example = new BDicCodeExample();
        example.or().andIdIn(idList).andDeletedEqualTo(false);
        example.setOrderByClause("mainid,ordernumber");
        return litemallDicCodeMapper.selectByExample(example);
    }

    public List getListCodeByTable(String tableName, String displayFieldName) {
        String strSql="select id,"+
                displayFieldName+" as name from "+
                tableName+" where deleted=0";
        Map<String, Object> param=new HashMap<>();
        param.put("sqlS",strSql);
        List codeList= commonDBService.procedureDaoList(param);
        return  codeList ;
    }


}
