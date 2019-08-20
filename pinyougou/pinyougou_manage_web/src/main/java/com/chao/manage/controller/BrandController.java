package com.chao.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chao.pojo.TbBrand;
import com.chao.sellergoods.service.BrandService;
import com.chao.vo.Result;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandController {
    @Reference
    private BrandService brandService;

    @GetMapping("/findAll")
    public List<TbBrand> findAll() {
        return brandService.findAll();
    }

    @GetMapping("/testPage")
    public List<TbBrand> testPage(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        return brandService.findPage(pageNum, pageSize).getList();
    }

    /**
     *  分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/findPage")
    public PageInfo<TbBrand> findPage(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return brandService.findPage(pageNum, pageSize);
    }

    /**
     *  添加
     * @param tbBrand
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody TbBrand tbBrand) {
        try {
            brandService.add(tbBrand);
            return Result.ok("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("保存失败");
    }

    /**
     *  修改页面查询数据显示
     * @param id
     * @return
     */
    @GetMapping("/findOne/{id}")
    public TbBrand findOne(@PathVariable Long id){
        return brandService.findOne(id);
    }

    /**
     *  修改
     * @param tbBrand
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody TbBrand tbBrand){
        try {
            brandService.update(tbBrand);
            return Result.ok("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("修改失败");
    }

    /**
     *  批量删除
     * @param ids
     * @return
     */
    @GetMapping("/delete")
    public Result deleteByIds(Long[] ids){
        try {
            brandService.deleteByIds(ids);
            return Result.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("删除失败");
    }

    /**
     *  根据分页条件查询
     * @param pageNum
     * @param pageSize
     * @param tbBrand
     * @return
     */
    @PostMapping("/search")
    public PageInfo<TbBrand> search(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                        @RequestBody TbBrand tbBrand){
        return brandService.search(pageNum,pageSize,tbBrand);
    }

    @GetMapping("/selectOptionList")
    public List<Map<String,Object>> selectOptionList(){
        return brandService.selectOptionList();
    }
}
