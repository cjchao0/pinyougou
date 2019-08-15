package com.chao.mapper;

import com.chao.pojo.TbBrand;

import java.util.List;

public interface BrandMapper extends BaseMapper<TbBrand>{
    List<TbBrand> queryAll();
}
