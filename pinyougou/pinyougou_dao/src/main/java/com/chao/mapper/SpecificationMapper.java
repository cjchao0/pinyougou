package com.chao.mapper;

import com.chao.pojo.TbSpecification;

import java.util.List;
import java.util.Map;

public interface SpecificationMapper extends BaseMapper<TbSpecification> {
    /**
     * 管理模板规范下拉框
     * @return
     */
    List<Map<String, Object>> selectOptionList();
}
