package com.chao.sellergoods.service;

import com.chao.pojo.TbBrand;
import com.chao.service.BaseService;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface BrandService extends BaseService<TbBrand> {
    List<TbBrand> queryAll();

    List<TbBrand> testPage(int pageNum,int pageSize);

    PageInfo<TbBrand> search(int pageNum, int pageSize, TbBrand tbBrand);

    List<Map<String, Object>> selectOptionList();
}
