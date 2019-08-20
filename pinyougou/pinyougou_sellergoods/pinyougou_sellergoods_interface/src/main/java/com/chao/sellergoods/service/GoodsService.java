package com.chao.sellergoods.service;

import com.chao.pojo.TbGoods;
import com.chao.service.BaseService;
import com.chao.vo.Goods;
import com.github.pagehelper.PageInfo;

public interface GoodsService extends BaseService<TbGoods> {
    /**
     * 根据条件搜索
     * @param pageNum 页号
     * @param pageSize 页面大小
     * @param goods 搜索条件
     * @return 分页信息
     */
    PageInfo<TbGoods> search(Integer pageNum, Integer pageSize, TbGoods goods);

    void addGoods(Goods goods);
}
