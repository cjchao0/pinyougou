package com.chao.mapper;

import com.chao.pojo.TbBrand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 *  通用mapper测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/*.xml")
public class BrandMapperTest {
    @Autowired
    private BrandMapper brandMapper;

    @Test
    public void queryAll(){
        List<TbBrand> list = brandMapper.queryAll();
        for (TbBrand brand : list) {
            System.out.println(brand);
        }
    }

    /**
     *  查询全部
     */
    @Test
    public void selectAll(){
        List<TbBrand> list = brandMapper.selectAll();
        for (TbBrand brand : list) {
            System.out.println(brand);
        }
    }

    /**
     *  根据主键查询
     */
    @Test
    public void selectByPrimaryKey(){
        TbBrand brand = brandMapper.selectByPrimaryKey(1L);
        System.out.println(brand);
    }

    /**
     *  根据条件查询
     */
    @Test
    public void select(){
        TbBrand tbBrand = new TbBrand();
        tbBrand.setFirstChar("C");
        List<TbBrand> brands = brandMapper.select(tbBrand);
        for (TbBrand brand : brands) {
            System.out.println(brand);
        }
    }

    /**
     *  添加
     */
    @Test
    public void insertSelective(){
        //INSERT INTO tb_brand ( id,name,first_char ) VALUES( ?,?,? )
        TbBrand brand = new TbBrand();
        brand.setName("test1");
        brand.setFirstChar("T");

        brandMapper.insertSelective(brand);
    }

    /**
     *  修改
     */
    @Test
    public void updateByPrimaryKeySelective(){
        //UPDATE tb_brand SET name = ? WHERE id = ?
        TbBrand brand = new TbBrand();
        brand.setId(23L);
        brand.setName("test2");

        brandMapper.updateByPrimaryKeySelective(brand);
    }

    /**
     *  根据主键删除
     */
    @Test
    public void deleteByPrimaryKeySelective(){
        //DELETE FROM tb_brand WHERE id = ?
        brandMapper.deleteByPrimaryKey(23L);
    }

    /**
     *  分页 排序 条件查询
     */
    @Test
    public void selectByExample(){
        //设置分页
        PageHelper.startPage(1,2);
        //查询条件构造对象
        Example example = new Example(TbBrand.class);
        //查询条件对象
        Example.Criteria criteria = example.createCriteria();
        //根据首字母查询
        criteria.andEqualTo("firstChar","C");
        //设置排序,根据id降序排序
        example.orderBy("id").desc();

        List<TbBrand> list = brandMapper.selectByExample(example);
        //转换为分页信息对象
        PageInfo<TbBrand> pageInfo = new PageInfo<>(list);

        System.out.println("符合本次查询的总记录数为: " + pageInfo.getTotal());
        System.out.println("符合本次查询的总页数为: " + pageInfo.getSize());
        System.out.println("符合本次查询的当前页号为: " + pageInfo.getPageNum());
        System.out.println("符合本次查询的页大小为: " + pageInfo.getPageSize());

        for (TbBrand tbBrand : pageInfo.getList()) {
            System.out.println(tbBrand);
        }
    }

    /**
     *  批量新增
     */
    @Test
    public void insertListMapper(){
        TbBrand tbBrand1 = new TbBrand();
        tbBrand1.setName("test3");
        tbBrand1.setFirstChar("T");

        TbBrand tbBrand2 = new TbBrand();
        tbBrand2.setName("test4");
        tbBrand2.setFirstChar("T");

        List<TbBrand> list = new ArrayList<>();
        list.add(tbBrand1);
        list.add(tbBrand2);
        //批量新增
        brandMapper.insertList(list);
    }

    /**
     *  批量删除
     */
    @Test
    public void deleteByIds(){
        Long[] ids = {24L,25L};
        //将 ids 数组拼接 24, 25
        String idStr = StringUtils.join(ids, ",");
        //批量删除
        brandMapper.deleteByIds(idStr);
    }
}
