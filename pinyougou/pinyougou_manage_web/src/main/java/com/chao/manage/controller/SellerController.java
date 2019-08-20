package com.chao.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chao.pojo.TbSeller;
import com.chao.sellergoods.service.SellerService;
import com.chao.vo.Result;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/seller")
@RestController
public class SellerController {

    @Reference
    private SellerService sellerService;

    /**
     * 新增
     * @param seller 实体
     * @return 操作结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody TbSeller seller){
        try {
            sellerService.add(seller);

            return Result.ok("新增成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("新增失败");
    }

    /**
     * 根据主键查询
     * @param id 主键
     * @return 实体
     */
    @GetMapping("/findOne/{id}")
    public TbSeller findOne(@PathVariable String id){
        return sellerService.findOne(id);
    }

    /**
     * 修改
     * @param seller 实体
     * @return 操作结果
     */
    @PostMapping("/update")
    public Result update(@RequestBody TbSeller seller){
        try {
            sellerService.update(seller);
            return Result.ok("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("修改失败");
    }

    /**
     * 根据主键数组批量删除
     * @param ids 主键数组
     * @return 实体
     */
    @GetMapping("/delete")
    public Result delete(String[] ids){
        try {
            sellerService.deleteByIds(ids);
            return Result.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("删除失败");
    }

    /**
     * 根据条件搜索
     * @param pageNum 页号
     * @param pageSize 页面大小
     * @param seller 搜索条件
     * @return 分页信息
     */
    @PostMapping("/search")
    public PageInfo<TbSeller> search(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                           @RequestBody TbSeller seller) {
        return sellerService.search(pageNum, pageSize, seller);
    }

}
