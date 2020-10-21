package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUITable;
import com.jt.vo.SysResult;
@RestController  //保证我们的返回值都是json时候使用
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	/**
	 * 根据条件查询数据信息
	 */
	@RequestMapping("/query")
	public EasyUITable findItemByPage(Integer page, Integer rows){
		return itemService.findItemByPage(page,rows);
	}
	/**
	 * 实现商品的新增
	 * Item.getItemDesc~~~itemDesc
	 */
	@RequestMapping("/save")
	public SysResult saveItem(Item item,ItemDesc itemDesc) {
			itemService.saveItem(item,itemDesc);
			return SysResult.success();
	}
	/**
	 * 实现商品的修改
	 */
	@RequestMapping("/update")
	public SysResult updateItem(Item item,ItemDesc itemDesc) {
		itemService.updateItem(item,itemDesc);
		return SysResult.success();
	}
	/**
	 * 实现商品的删除
	 */
	@RequestMapping("/delete")
	public SysResult deleteItem(Long[] ids) {
		itemService.deleteItem(ids);
		return SysResult.success();
	}
	/**
	 * 实现商品的下架
	 * URL:/item/instock  从前台浏览器请求路径获取
	 * type:post
	 * params:
	 */
	@RequestMapping("/instock")
	public SysResult instockItem(Long[] ids) {
		int status =2;// 表示下架
		itemService.updateItemStatus(ids,status);
		return SysResult.success();
	}
	/**
	 * 实现商品的上架
	 */
	@RequestMapping("/reshelf")
	public SysResult reshelfItem(Long[] ids) {
		int status =1;// 表示上架
		itemService.updateItemStatus(ids,status);
		return SysResult.success();
	}
	/**
	 * 根据Id查询商品详情信息
	 */
	@RequestMapping("/query/item/desc/{itemId}")
	public SysResult findItemDescById
	(@PathVariable Long itemId) {
		ItemDesc desc=itemService.findItemDescById(itemId);
		return SysResult.success(desc);
	}
}
