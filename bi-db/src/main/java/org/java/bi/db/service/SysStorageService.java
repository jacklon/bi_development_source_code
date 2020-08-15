package org.java.bi.db.service;

import com.github.pagehelper.PageHelper;
import org.java.bi.db.dao.SysStorageMapper;
import org.java.bi.db.domain.SysStorage;
import org.java.bi.db.domain.SysStorageExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SysStorageService {

    @Resource
    private SysStorageMapper storageMapper;

    public void deleteByKey(String key) {
        SysStorageExample example = new SysStorageExample();
        example.or().andKeyEqualTo(key);
        storageMapper.logicalDeleteByExample(example);
    }

    public void add(SysStorage storageInfo) {
        storageInfo.setAddTime(LocalDateTime.now());
        storageInfo.setUpdateTime(LocalDateTime.now());
        storageMapper.insertSelective(storageInfo);
    }

    public SysStorage findByKey(String key) {
        SysStorageExample example = new SysStorageExample();
        example.or().andKeyEqualTo(key).andDeletedEqualTo(false);
        return storageMapper.selectOneByExample(example);
    }

    public int update(SysStorage storageInfo) {
        storageInfo.setUpdateTime(LocalDateTime.now());
        return storageMapper.updateByPrimaryKeySelective(storageInfo);
    }

    public SysStorage findById(Integer id) {
        return storageMapper.selectByPrimaryKey(id);
    }

    public List<SysStorage> querySelective(String key, String name, Integer page, Integer limit, String sort) {
        SysStorageExample example = new SysStorageExample();
        SysStorageExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(key)) {
            criteria.andKeyEqualTo(key);
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort)) {
            example.setOrderByClause(sort);
        }

        PageHelper.startPage(page, limit);
        return storageMapper.selectByExample(example);
    }
}
