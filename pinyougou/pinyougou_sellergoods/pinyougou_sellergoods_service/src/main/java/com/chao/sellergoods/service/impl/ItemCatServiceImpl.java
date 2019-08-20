package com.chao.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.chao.mapper.ItemCatMapper;
import com.chao.pojo.TbItemCat;
import com.chao.sellergoods.service.ItemCatService;
import com.chao.service.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ItemCatServiceImpl extends BaseServiceImpl<TbItemCat> implements ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;

    @Override
    public PageInfo<TbItemCat> search(Integer pageNum, Integer pageSize, TbItemCat itemCat) {
        //设置分页
        PageHelper.startPage(pageNum, pageSize);
        //创建查询对象
        Example example = new Example(TbItemCat.class);

        //创建查询条件对象
        Example.Criteria criteria = example.createCriteria();

        //模糊查询
        if (StringUtils.isNotBlank(itemCat.getName())) {
            criteria.andLike("name", "%" + itemCat.getName() + "%");
        }

        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

}
