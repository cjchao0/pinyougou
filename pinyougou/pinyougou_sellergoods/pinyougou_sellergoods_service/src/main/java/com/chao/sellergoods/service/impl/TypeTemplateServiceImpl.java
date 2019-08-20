package com.chao.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.chao.mapper.SpecificationMapper;
import com.chao.mapper.SpecificationOptionMapper;
import com.chao.mapper.TypeTemplateMapper;
import com.chao.pojo.TbSpecificationOption;
import com.chao.pojo.TbTypeTemplate;
import com.chao.sellergoods.service.TypeTemplateService;
import com.chao.service.impl.BaseServiceImpl;
import com.chao.vo.Specification;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class TypeTemplateServiceImpl extends BaseServiceImpl<TbTypeTemplate> implements TypeTemplateService {

    @Autowired
    private TypeTemplateMapper typeTemplateMapper;
    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;


    @Override
    public PageInfo<TbTypeTemplate> search(Integer pageNum, Integer pageSize, TbTypeTemplate typeTemplate) {
        //设置分页
        PageHelper.startPage(pageNum, pageSize);
        //创建查询对象
        Example example = new Example(TbTypeTemplate.class);

        //创建查询条件对象
        Example.Criteria criteria = example.createCriteria();

        //模糊查询
        if (StringUtils.isNotBlank(typeTemplate.getName())) {
            criteria.andLike("name", "%" + typeTemplate.getName() + "%");
        }

        List<TbTypeTemplate> list = typeTemplateMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

    @Override
    public List<Map> findSpecList(Long id) {
        //查询规格选项
        TbTypeTemplate typeTemplate = findOne(id);
        //获取规格模板并转换为List
        List<Map> specList = JSONArray.parseArray(typeTemplate.getSpecIds(), Map.class);
        for (Map map : specList) {
            //查询规格对应的选项
            TbSpecificationOption param = new TbSpecificationOption();
            param.setSpecId(Long.parseLong(map.get("id").toString()));
            List<TbSpecificationOption> options = specificationOptionMapper.select(param);

            map.put("options",options);
        }
        return specList;
    }

}
