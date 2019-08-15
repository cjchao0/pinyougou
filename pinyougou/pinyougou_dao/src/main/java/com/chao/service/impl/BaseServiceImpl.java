package com.chao.service.impl;

import com.chao.mapper.BaseMapper;
import com.chao.service.BaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public abstract class BaseServiceImpl<T> implements BaseService<T> {
    @Autowired
    private BaseMapper<T> baseMapper;

    @Override
    public List<T> findAll() {
        return baseMapper.selectAll();
    }

    @Override
    public T findOne(Serializable id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> findByWhere(T t) {
        return baseMapper.select(t);
    }

    @Override
    public PageInfo<T> findPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<T> list = baseMapper.selectAll();
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<T> findPage(Integer pageNum, Integer pageSize, T t) {
        PageHelper.startPage(pageNum, pageSize);
        List<T> list = baseMapper.select(t);
        return new PageInfo<>(list);
    }

    @Override
    public void add(T t) {
        baseMapper.insertSelective(t);
    }

    @Override
    public void update(T t) {
        baseMapper.updateByPrimaryKeySelective(t);
    }

    @Override
    public void deleteByIds(Serializable[] ids) {
        if (ids != null && ids.length > 0) {
            baseMapper.deleteByIds(StringUtils.join(ids,","));
        }
    }
}
