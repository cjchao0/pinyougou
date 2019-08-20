package com.chao.sellergoods.service;

import com.chao.vo.Specification;
import com.chao.pojo.TbSpecification;
import com.chao.service.BaseService;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface SpecificationService extends BaseService<TbSpecification> {
    /**
     * 根据条件搜索
     * @param pageNum 页号
     * @param pageSize 页面大小
     * @param specification 搜索条件
     * @return 分页信息
     */
    PageInfo<TbSpecification> search(Integer pageNum, Integer pageSize, TbSpecification specification);

    void addSpecification(Specification specification);

    Specification findSpecificationById(Long id);

    void updateSpecification(Specification specification);

    void deleteSpecificationByIds(Long[] ids);

    /**
     * 管理模板规范下拉框
     * @return
     */
    List<Map<String, Object>> selectOptionList();
}
