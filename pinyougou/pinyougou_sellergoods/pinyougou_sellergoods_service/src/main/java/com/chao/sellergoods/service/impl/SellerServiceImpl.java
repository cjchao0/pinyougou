package com.chao.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.chao.mapper.SellerMapper;
import com.chao.pojo.TbSeller;
import com.chao.sellergoods.service.SellerService;
import com.chao.service.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SellerServiceImpl extends BaseServiceImpl<TbSeller> implements SellerService {

    @Autowired
    private SellerMapper sellerMapper;

    @Override
    public PageInfo<TbSeller> search(Integer pageNum, Integer pageSize, TbSeller seller) {
        //设置分页
        PageHelper.startPage(pageNum, pageSize);
        //创建查询对象
        Example example = new Example(TbSeller.class);

        //创建查询条件对象
        Example.Criteria criteria = example.createCriteria();

        //状态查询
        if(StringUtils.isNotBlank(seller.getStatus())){
            criteria.andEqualTo("status",seller.getStatus());
        }
        //公司名称查询
        if (StringUtils.isNotBlank(seller.getName())) {
            criteria.andLike("name", "%" + seller.getName() + "%");
        }
        //店铺名称查询
        if(StringUtils.isNotBlank(seller.getNickName())){
            criteria.andLike("nickName","%" + seller.getNickName() + "%");
        }

        List<TbSeller> list = sellerMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

}
