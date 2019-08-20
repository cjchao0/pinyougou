package com.chao.vo;

import com.chao.pojo.TbSpecification;
import com.chao.pojo.TbSpecificationOption;

import java.io.Serializable;
import java.util.List;

public class Specification implements Serializable {

    private TbSpecification Specification;

    private List<TbSpecificationOption> SpecificationOptionList;

    private static final long serialVersionUID = 1L;

    public TbSpecification getSpecification() {
        return Specification;
    }

    public void setSpecification(TbSpecification Specification) {
        this.Specification = Specification;
    }

    public List<TbSpecificationOption> getSpecificationOptionList() {
        return SpecificationOptionList;
    }

    public void setSpecificationOptionList(List<TbSpecificationOption> specificationOptionList) {
        SpecificationOptionList = specificationOptionList;
    }
}
