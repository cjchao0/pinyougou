package com.chao.sellergoods.service;

import com.chao.pojo.TbTypeTemplate;
import com.chao.service.BaseService;
import com.github.pagehelper.PageInfo;

public interface TypeTemplateService extends BaseService<TbTypeTemplate> {
    /**
     * 根据条件搜索
     * @param pageNum 页号
     * @param pageSize 页面大小
     * @param typeTemplate 搜索条件
     * @return 分页信息
     */
    PageInfo<TbTypeTemplate> search(Integer pageNum, Integer pageSize, TbTypeTemplate typeTemplate);

}
