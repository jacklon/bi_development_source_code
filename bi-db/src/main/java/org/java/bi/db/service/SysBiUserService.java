package org.java.bi.db.service;

import org.java.bi.db.dao.SysBiUserMapper;
import org.java.bi.db.domain.SysBiUser;
import org.java.bi.db.domain.SysBiUserExample;
import org.java.bi.db.util.MapConvertCamel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class SysBiUserService {
    private final SysBiUser.Column[] result = new SysBiUser.Column[]{SysBiUser.Column.id, SysBiUser.Column.username,
            SysBiUser.Column.avatar, SysBiUser.Column.roleIds, SysBiUser.Column.deptId};
    @Resource
    private SysBiUserMapper adminMapper;

    @Autowired
    private CommonDBService commonDBService;


    public List<SysBiUser> findAdmin(String username) {
        SysBiUserExample example = new SysBiUserExample();
        example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
        return adminMapper.selectByExample(example);
    }

    public SysBiUser findAdmin(Integer id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    public Map<String, Object> querySelective(String username,String telphone,Boolean stopFlag,List<Integer[]> roleIds,
                                              List deptIds, Integer page, Integer limit, String sort) {
        String strSql="select sql_calc_found_rows * from litemall_admin where deleted=0 ";
        String condition="";
        if (!StringUtils.isEmpty(username)) {
            condition+=" and (username like '%"+username+"%' or getPY(username) like '%"+username+"%')";
        }
        if (!StringUtils.isEmpty(telphone)) {
            condition+=" and (telphone like '%"+telphone+"%')";
        }
        if (!StringUtils.isEmpty(stopFlag)) {
            condition+=" and stop_flag="+(stopFlag.equals(true)?1:0);
        }
        if (!StringUtils.isEmpty(roleIds)) {
//            condition+=commonDBService.getFindListIntegerArrayInConditon("role_ids",roleIds);
            condition+=commonDBService.getFindListIntegerArrayJsonConditon("role_ids",roleIds);

        }
        if (!StringUtils.isEmpty(deptIds)) {
            condition+=commonDBService.getFindListIntegerInConditon("dept_id",deptIds);
        }

        if (!StringUtils.isEmpty(sort)) {
            condition+=" order by "+sort;

        }
        //构造分页
        String pageSql="";
        //如果传入的参数是999999,则表示不分页
        if(limit<999999) {
            int fromIndex = (page - 1) * limit;
            pageSql += " limit " + fromIndex + "," + limit;
        }
        strSql=strSql+condition+pageSql;
        Map<String, Object> param=new HashMap<>();
        param.put("sqlS",strSql);

        List<Map<String, Object>> dataList= commonDBService.procedureDaoList(param);
        Integer count=(Integer) param.get("total");
//        List<Map<String, Object>> convertResult=new ArrayList<>();
        LinkedList<Map<String, Object>> convertResult=new LinkedList();

        Map<String, Object> result=new HashMap<>();
        result.put("data",convertResult);
        result.put("total",count);

        return result;

    }

    public List<SysBiUser> queryall() {
        SysBiUserExample example = new SysBiUserExample();
        SysBiUserExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andStopFlagEqualTo(false);

        return adminMapper.selectByExampleSelective(example);
    }

    public List<SysBiUser> queryallByDeptId(Integer deptId) {
        SysBiUserExample example = new SysBiUserExample();
        SysBiUserExample.Criteria criteria = example.createCriteria();
        criteria.andDeptIdEqualTo(deptId);
        criteria.andDeletedEqualTo(false);
        criteria.andStopFlagEqualTo(false);

        return adminMapper.selectByExampleSelective(example);
    }

    public int updateById(SysBiUser admin) {
        admin.setUpdateTime(LocalDateTime.now());
        return adminMapper.updateByPrimaryKeySelective(admin);
    }

    public int updateAllById(SysBiUser admin) {
        admin.setUpdateTime(LocalDateTime.now());
        return adminMapper.updateByPrimaryKey(admin);
    }

    public void deleteById(Integer id) {
        adminMapper.logicalDeleteByPrimaryKey(id);

    }

    public void add(SysBiUser admin) {
        admin.setAddTime(LocalDateTime.now());
        admin.setUpdateTime(LocalDateTime.now());
        adminMapper.insertSelective(admin);
    }

    public SysBiUser findById(Integer id) {
        return adminMapper.selectByPrimaryKeySelective(id);
    }

    public List<SysBiUser> findByIdList(List<Integer> idList) {
        SysBiUserExample example = new SysBiUserExample();
        SysBiUserExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(idList);
        return adminMapper.selectByExample(example);
    }

    public SysBiUser findByTelphone(String strTelphone) {
        SysBiUserExample example = new SysBiUserExample();
        SysBiUserExample.Criteria criteria = example.createCriteria();
        criteria.andTelphoneEqualTo(strTelphone);
        return adminMapper.selectOneByExample(example);
    }

    public List<SysBiUser> all() {
        SysBiUserExample example = new SysBiUserExample();
        example.or().andDeletedEqualTo(false);
        return adminMapper.selectByExample(example);
    }



}
