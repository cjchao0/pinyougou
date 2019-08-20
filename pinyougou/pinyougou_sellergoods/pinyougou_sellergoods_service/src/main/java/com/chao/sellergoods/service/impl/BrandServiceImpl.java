package com.chao.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.chao.mapper.BrandMapper;
import com.chao.pojo.TbBrand;
import com.chao.sellergoods.service.BrandService;
import com.chao.service.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl extends BaseServiceImpl<TbBrand> implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    public List<TbBrand> queryAll() {
        return brandMapper.queryAll();
    }

    @Override
    public List<TbBrand> testPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return brandMapper.selectAll();
    }

    /**
     *  根据分页条件查询
     * @param pageNum
     * @param pageSize
     * @param tbBrand
     * @return
     */
    @Override
    public PageInfo<TbBrand> search(int pageNum, int pageSize, TbBrand tbBrand) {
            //设置分页
            PageHelper.startPage(pageNum, pageSize);
            //根据品牌名称,首字母进行条件模糊查询
            //select * from tb_brand where name like '%?%' and first_char = ?
            //查询条件构造对象
            Example example = new Example(TbBrand.class);
            Example.Criteria criteria = example.createCriteria();
            //首字母
            // if(brand.getFirstChar() != null && !brand.getFirstChar().equals(""))
            if (StringUtils.isNotBlank(tbBrand.getFirstChar())) {
                //根据首字母查询
                criteria.andEqualTo("firstChar", tbBrand.getFirstChar());
            }
            //品牌名称
            if(StringUtils.isNotBlank(tbBrand.getName())) {
                //根据名称模糊搜索
                criteria.andLike("name", "%" + tbBrand.getName() + "%");
            }
            List<TbBrand> list = brandMapper.selectByExample(example);
            //转换为分页信息对象
            return new PageInfo<>(list);
    }

    @Override
    public List<Map<String, Object>> selectOptionList() {
        return brandMapper.selectOptionList();
    }
}
