package com.jt.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUITable;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;

	/**
	 * 查询商品的总记录数 进行分页查询 分页sql :分页20条 第一页 select *from tb_item limit 0,20 [0,19] 第二页
	 * select *from tb_item limit 20,20 [20,39] 第三页 select *from tb_item limit 40,20
	 * [40,59] 第n页 select *from tb_item limit (page-1)*rows,rows
	 */

	@Override
	public EasyUITable findItemByPage(Integer page, Integer rows) {
//		int total =itemMapper.selectCount(null);
//		int start =(page-1)*rows;
//		List<Item> userList = itemMapper.findItemByPage(start,rows);
		Page<Item> tempPage = new Page<>(page, rows);
		QueryWrapper<Item> queryWrapper = new QueryWrapper<Item>();
		queryWrapper.orderByDesc("updated");
		IPage<Item> Ipage = itemMapper.selectPage(tempPage, queryWrapper);
		// 获取总记录数
		int total = (int) Ipage.getTotal();
		// 获取分页的结果
		List<Item> userList = Ipage.getRecords();
		return new EasyUITable(total, userList);
	}

	/**
	 * 商品的新增
	 */
	@Override
	@Transactional // 事务控制
	public void saveItem(Item item, ItemDesc itemDesc) {
		item.setStatus(1);
		item.setCreated(new Date());
		item.setUpdated(item.getCreated());
		itemMapper.insert(item);
		// 富文本编辑器
		// 利用mybatis-plus
		// 入库自动将商品id进行回显
		itemDesc.setItemId(item.getId());
		itemDesc.setCreated(item.getCreated());
		itemDesc.setUpdated(item.getCreated());
		itemDescMapper.insert(itemDesc);
	}

	/**
	 * 商品的修改
	 */
	@Override
	@Transactional // 控制事务
	public void updateItem(Item item, ItemDesc itemDesc) {
		item.setUpdated(new Date());
		itemMapper.updateById(item);
		// 所有数据都更新
		// 富文本编辑器
		itemDesc.setItemId(item.getId())
				.setUpdated(item.getUpdated());
		itemDescMapper.updateById(itemDesc);

	}

	/**
	 * 商品的删除
	 */
	@Override
	@Transactional
	public void deleteItem(Long[] ids) {
		List<Long> idList = Arrays.asList(ids);
		itemMapper.deleteBatchIds(idList);
		itemDescMapper.deleteBatchIds(idList);
	}

	/**
	 * 商品的上架 任务:将ids中所有的数据的状态:status改为2
	 */
	@Override
	public void updateItemStatus(Long[] ids, int status) {
//		//1.小白级别
//		for (Long id : ids) {
//			Item item =new Item();
//			item.setId(id)
//			.setStatus(status)
//			.setUpdated(new Date());
//			itemMapper.updateById(item);
//		}
		// 2.菜鸟级别
		Item item = new Item();
		item.setStatus(status).setUpdated(new Date());
		UpdateWrapper<Item> updateWrapper = new UpdateWrapper<Item>();
		List<Long> idList = Arrays.asList(ids);
		updateWrapper.in("id", idList);
		itemMapper.update(item, updateWrapper);
	}

	@Override
	public ItemDesc findItemDescById(Long itemId) {
		return itemDescMapper.selectById(itemId);
	}

	@Override
	public Item findItemById(Long itemId) {
		return itemMapper.selectById(itemId);
	}

}
