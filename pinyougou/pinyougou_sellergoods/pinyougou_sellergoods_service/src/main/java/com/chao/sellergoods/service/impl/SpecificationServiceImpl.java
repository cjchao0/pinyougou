package com.chao.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.chao.mapper.SpecificationMapper;
import com.chao.mapper.SpecificationOptionMapper;
import com.chao.pojo.TbSpecificationOption;
import com.chao.vo.Specification;
import com.chao.pojo.TbSpecification;
import com.chao.sellergoods.service.SpecificationService;
import com.chao.service.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class SpecificationServiceImpl extends BaseServiceImpl<TbSpecification> implements SpecificationService {

    @Autowired
    private SpecificationMapper specificationMapper;

    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    @Override
    public PageInfo<TbSpecification> search(Integer pageNum, Integer pageSize, TbSpecification specification) {
        //设置分页
        PageHelper.startPage(pageNum, pageSize);
        //创建查询对象
        Example example = new Example(TbSpecification.class);

        //创建查询条件对象
        Example.Criteria criteria = example.createCriteria();

        //模糊查询
        if (StringUtils.isNotBlank(specification.getSpecName())) {
            criteria.andLike("specName", "%" + specification.getSpecName() + "%");
        }

        List<TbSpecification> list = specificationMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

    /**
     *  规范 和 选项 添加
     * @param specification
     */
    @Override
    public void addSpecification(Specification specification) {
        //添加规范
        add(specification.getSpecification());

        //遍历规范选项
        for (TbSpecificationOption specificationOption : specification.getSpecificationOptionList()){
            //把规范id存入规范选项specid
            specificationOption.setSpecId(specification.getSpecification().getId());
        }
        //批量插入
        specificationOptionMapper.insertList(specification.getSpecificationOptionList());
    }

    /**
     *  规范 和 选项 查询
     * @param id
     * @return
     */
    @Override
    public Specification findSpecificationById(Long id) {
        Specification specification = new Specification();

        //实例规范选项
        TbSpecificationOption specificationOption = new TbSpecificationOption();
        specificationOption.setSpecId(id);

        //根据id查询规范选项
        List<TbSpecificationOption> specificationOptionList = specificationOptionMapper.select(specificationOption);

        //把查询的数据存入specification
        specification.setSpecification(findOne(id));
        specification.setSpecificationOptionList(specificationOptionList);

        //返回
        return specification;
    }

    /**
     *  规范 和 规范选项 更新
     * @param specification
     */
    @Override
    public void updateSpecification(Specification specification) {
        //更新规范
        update(specification.getSpecification());

        //实例 规范选项
        TbSpecificationOption specificationOption = new TbSpecificationOption();
        //把规范选项specId设成规范id
        specificationOption.setSpecId(specification.getSpecification().getId());
        //删除
        specificationOptionMapper.delete(specificationOption);

        //遍历规范选项
        for (TbSpecificationOption tbSpecificationOption : specification.getSpecificationOptionList()) {
            ////把规范选项specId设成规范id
            tbSpecificationOption.setSpecId(specification.getSpecification().getId());
        }
        //批量插入
        specificationOptionMapper.insertList(specification.getSpecificationOptionList());
    }

    @Override
    public void deleteSpecificationByIds(Long[] ids) {
        //- 根据规格id数组批量删除规格
        //    -- 根据规格id数组批量删除规格
        //    DELETE FROM  `tb_specification` WHERE `id` IN (?,?,?) ;
        deleteByIds(ids);

        /*TbSpecificationOption specificationOption = new TbSpecificationOption();
        for (Long id : ids) {
            specificationOption.setSpecId(id);
            specificationOptionMapper.delete(specificationOption);
        }*/

        //- 根据规格id数组批量删除规格选项
        //    -- 根据规格id数组批量删除规格选项
        //    DELETE FROM  `tb_specification_option` WHERE `spec_id` IN (?,?,?) ;
        Example example = new Example(TbSpecification.class);
        example.createCriteria().andIn("specId", Arrays.asList(ids));
        specificationOptionMapper.deleteByExample(example);
    }

    /**
     *  管理模板规范下拉框
     * @return
     */
    @Override
    public List<Map<String, Object>> selectOptionList() {
        return specificationMapper.selectOptionList();
    }

}
