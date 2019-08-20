package com.chao.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.chao.mapper.*;
import com.chao.pojo.*;
import com.chao.sellergoods.service.GoodsService;
import com.chao.service.impl.BaseServiceImpl;
import com.chao.vo.Goods;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImpl extends BaseServiceImpl<TbGoods> implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsDescMapper goodsDescMapper;
    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired
    private SellerMapper sellerMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private ItemMapper itemMapper;

    @Override
    public PageInfo<TbGoods> search(Integer pageNum, Integer pageSize, TbGoods goods) {
        //设置分页
        PageHelper.startPage(pageNum, pageSize);
        //创建查询对象
        Example example = new Example(TbGoods.class);

        //创建查询条件对象
        Example.Criteria criteria = example.createCriteria();

        //模糊查询
        if (StringUtils.isNotBlank(goods.getGoodsName())) {
            criteria.andLike("goodsName", "%" + goods.getGoodsName() + "%");
        }

        List<TbGoods> list = goodsMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

    @Override
    public void addGoods(Goods goods) {
        //新增商品基本信息
        add(goods.getGoods());

        //新增商品描述信息
        goods.getGoodsDesc().setGoodsId(goods.getGoods().getId());
        goodsDescMapper.insert(goods.getGoodsDesc());

        //保存商品sku列表
        saveItemList(goods);
    }

    /**
     * 保存商品sku
     *
     * @param goods 商品信息（基本、描述、sku列表）
     */
    private void saveItemList(Goods goods) {
        if ("1".equals(goods.getGoods().getIsEnableSpec())) {
            //启用规格
            if (goods.getItemList() != null && goods.getItemList().size() > 0) {
                for (TbItem tbItem : goods.getItemList()) {
                    //标题:spu商品名称 + 当前这个sku的所有规格选项值拼接而成
                    String title = goods.getGoods().getGoodsName();
                    //将规格转换为对象,然后将其属性拼接到标题中
                    Map<String, String> specMap = JSON.parseObject(tbItem.getSpec(), Map.class);
                    for (Map.Entry<String, String> entry : specMap.entrySet()) {
                        title += " " + entry.getValue();
                    }
                    tbItem.setTitle(title);

                    setItemValue(tbItem, goods);

                    //保存商品sku
                    itemMapper.insertSelective(tbItem);
                }
            }
        } else {
            //不启用规格
            TbItem tbItem = new TbItem();
            //价格
            tbItem.setPrice(goods.getGoods().getPrice());
            //库存
            tbItem.setNum(9999);
            //是否启用;不启用
            tbItem.setStatus("0");
            //是否默认
            tbItem.setIsDefault("1");
            //规格
            tbItem.setSpec("{}");

            tbItem.setTitle(goods.getGoods().getGoodsName());

            setItemValue(tbItem,goods);

            itemMapper.insertSelective(tbItem);
        }
    }

    /**
     *  设置sku商品信息
     * @param tbItem
     * @param goods
     */
    private void setItemValue(TbItem tbItem, Goods goods) {
        //图片,获取spu的第1张图片
        if (StringUtils.isNotBlank(goods.getGoodsDesc().getItemImages())) {
            List<Map> imageList = JSON.parseArray(goods.getGoodsDesc().getItemImages(), Map.class);
            if (imageList != null && imageList.size() > 0) {
                tbItem.setImage(imageList.get(0).get("url").toString());
            }
        }
        tbItem.setGoodsId(goods.getGoods().getId());
        //未审核
        tbItem.setStatus("0");
        //商家
        TbSeller seller = sellerMapper.selectByPrimaryKey(goods.getGoods().getSellerId());
        tbItem.setSellerId(seller.getSellerId());
        tbItem.setSeller(seller.getName());
        //品牌
        TbBrand brand = brandMapper.selectByPrimaryKey(goods.getGoods().getBrandId());
        tbItem.setBrand(brand.getName());
        //分类:使用商品spu中的第3级商品分类
        TbItemCat itemCat = itemCatMapper.selectByPrimaryKey(goods.getGoods().getCategory3Id());
        tbItem.setCategoryid(itemCat.getId());
        tbItem.setCategory(itemCat.getName());

        tbItem.setCreateTime(new Date());
        tbItem.setUpdateTime(tbItem.getCreateTime());
    }
}
