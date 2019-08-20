package com.chao.sellergoods.service;

import com.chao.pojo.TbSeller;
import com.chao.service.BaseService;
import com.github.pagehelper.PageInfo;

public interface SellerService extends BaseService<TbSeller> {
    /**
     * 根据条件搜索
     * @param pageNum 页号
     * @param pageSize 页面大小
     * @param seller 搜索条件
     * @return 分页信息
     */
    PageInfo<TbSeller> search(Integer pageNum, Integer pageSize, TbSeller seller);

}
