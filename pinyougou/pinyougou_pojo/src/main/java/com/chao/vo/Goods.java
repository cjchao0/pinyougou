package com.chao.vo;

import com.chao.pojo.TbGoods;
import com.chao.pojo.TbGoodsDesc;
import com.chao.pojo.TbItem;

import java.io.Serializable;
import java.util.List;

public class Goods implements Serializable {
    private TbGoods Goods;
    private TbGoodsDesc GoodsDesc;
    private List<TbItem> itemList;

    public TbGoods getGoods() {
        return Goods;
    }

    public void setGoods(TbGoods Goods) {
        this.Goods = Goods;
    }

    public TbGoodsDesc getGoodsDesc() {
        return GoodsDesc;
    }

    public void setGoodsDesc(TbGoodsDesc GoodsDesc) {
        this.GoodsDesc = GoodsDesc;
    }

    public List<TbItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<TbItem> itemList) {
        this.itemList = itemList;
    }
}
