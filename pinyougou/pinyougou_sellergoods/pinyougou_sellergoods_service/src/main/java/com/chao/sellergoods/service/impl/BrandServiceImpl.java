package com.chao.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.chao.mapper.BrandMapper;
import com.chao.pojo.TbBrand;
import com.chao.sellergoods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService{
    @Autowired
    private BrandMapper brandMapper;

    public List<TbBrand> queryAll(){
        return brandMapper.queryAll();
    }
}
