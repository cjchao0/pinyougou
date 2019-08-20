package com.chao.mapper;

import com.chao.pojo.TbBrand;

import java.util.List;
import java.util.Map;

public interface BrandMapper extends BaseMapper<TbBrand>{
    List<TbBrand> queryAll();

    List<Map<String, Object>> selectOptionList();
}
