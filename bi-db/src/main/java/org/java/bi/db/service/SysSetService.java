package org.java.bi.db.service;

import org.java.bi.db.dao.SysSetMapper;
import org.java.bi.db.domain.SysSet;
import org.java.bi.db.domain.SysSetExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysSetService {
    @Resource
    private SysSetMapper systemMapper;

    public Map<String, String> queryAll() {
        SysSetExample example = new SysSetExample();
        example.or().andDeletedEqualTo(false);

        List<SysSet> systemList = systemMapper.selectByExample(example);
        Map<String, String> systemConfigs = new HashMap<>();
        for (SysSet item : systemList) {
            systemConfigs.put(item.getKeyName(), item.getKeyValue());
        }

        return systemConfigs;
    }

    public Map<String, String> listMall() {
        SysSetExample example = new SysSetExample();
        example.or().andKeyNameLike("litemall_mall_%").andDeletedEqualTo(false);
        List<SysSet> systemList = systemMapper.selectByExample(example);
        Map<String, String> data = new HashMap<>();
        for(SysSet system : systemList){
            data.put(system.getKeyName(), system.getKeyValue());
        }
        return data;
    }

    public Map<String, String> listWx() {
        SysSetExample example = new SysSetExample();
        example.or().andKeyNameLike("litemall_wx_%").andDeletedEqualTo(false);
        List<SysSet> systemList = systemMapper.selectByExample(example);
        Map<String, String> data = new HashMap<>();
        for(SysSet system : systemList){
            data.put(system.getKeyName(), system.getKeyValue());
        }
        return data;
    }

    /**
     * 获取默认微商城设置
     * @return
     */
    public Map<String, String> listWeiShang() {
        SysSetExample example = new SysSetExample();
        example.or().andKeyNameLike("litemall_weishang_%").andDeletedEqualTo(false);
        List<SysSet> systemList = systemMapper.selectByExample(example);
        Map<String, String> data = new HashMap<>();
        for(SysSet system : systemList){
            data.put(system.getKeyName(), system.getKeyValue());
        }
        return data;
    }

    /**
     * 获取系统参数设置
     * @return
     */
    public Map<String, String> listSysParas() {
        SysSetExample example = new SysSetExample();
        example.or().andKeyNameLike("sys_para_%").andDeletedEqualTo(false);
        List<SysSet> systemList = systemMapper.selectByExample(example);
        Map<String, String> data = new HashMap<>();
        for(SysSet system : systemList){
            data.put(system.getKeyName(), system.getKeyValue());
        }
        return data;
    }

    public Map<String, String> listOrder() {
        SysSetExample example = new SysSetExample();
        example.or().andKeyNameLike("litemall_order_%").andDeletedEqualTo(false);
        List<SysSet> systemList = systemMapper.selectByExample(example);
        Map<String, String> data = new HashMap<>();
        for(SysSet system : systemList){
            data.put(system.getKeyName(), system.getKeyValue());
        }
        return data;
    }

    public Map<String, String> listExpress() {
        SysSetExample example = new SysSetExample();
        example.or().andKeyNameLike("litemall_express_%").andDeletedEqualTo(false);
        List<SysSet> systemList = systemMapper.selectByExample(example);
        Map<String, String> data = new HashMap<>();
        for(SysSet system : systemList){
            data.put(system.getKeyName(), system.getKeyValue());
        }
        return data;
    }

    public void updateConfig(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            SysSetExample example = new SysSetExample();
            example.or().andKeyNameEqualTo(entry.getKey()).andDeletedEqualTo(false);
            List<SysSet> litemallSystems= systemMapper.selectByExample(example);
            if(litemallSystems.size()>0) {
                SysSet system = new SysSet();
                system.setKeyName(entry.getKey());
                system.setKeyValue(entry.getValue());
                system.setUpdateTime(LocalDateTime.now());
                systemMapper.updateByExampleSelective(system, example);

            } else
            {
                addConfig(entry.getKey(),entry.getValue());
            }
            //重新加载系统配置
        }

    }

    public void addConfig(String key, String value) {
        SysSet system = new SysSet();
        system.setKeyName(key);
        system.setKeyValue(value);
        system.setAddTime(LocalDateTime.now());
        system.setUpdateTime(LocalDateTime.now());
        systemMapper.insertSelective(system);
    }

    public Boolean ifDisplayGoodsWhenStoreEqualZero(){
        SysSetExample example = new SysSetExample();
        example.or().andKeyNameLike("litemall_mall_goods_display_when_zero").andDeletedEqualTo(false);
        List<SysSet> systemList = systemMapper.selectByExample(example);
        if(systemList==null||systemList.size()==0){
            return true;
        } else
        {
            if(systemList.get(0).getKeyValue().equalsIgnoreCase("true")){
                return true;
            } else
            {
                return false;
            }
        }

    }
}

