package com.chao.sellergoods.service;

import com.chao.pojo.TbItemCat;
import com.chao.service.BaseService;
import com.github.pagehelper.PageInfo;

public interface ItemCatService extends BaseService<TbItemCat> {
    /**
     * 根据条件搜索
     * @param pageNum 页号
     * @param pageSize 页面大小
     * @param itemCat 搜索条件
     * @return 分页信息
     */
    PageInfo<TbItemCat> search(Integer pageNum, Integer pageSize, TbItemCat itemCat);

}
