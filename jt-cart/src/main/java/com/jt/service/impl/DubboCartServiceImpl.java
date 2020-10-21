package com.jt.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;
import com.jt.service.DubboCartService;

@Service(timeout = 3000)
public class DubboCartServiceImpl implements DubboCartService {
	@Autowired
	private CartMapper cartMapper;

	@Override
	public List<Cart> findCartListByUserId(Long userId) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<Cart>();
		queryWrapper.eq("user_id", userId);
		return cartMapper.selectList(queryWrapper);
	}

	@Override
	public void deleteCart(Cart cart) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<Cart>();
		cartMapper.delete(queryWrapper);

	}

	/**
	 * 1.判断购物车中是否有记录 null:新增 !null: num,updated 更新操作
	 */
	@Override
	public void saveCart(Cart cart) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<Cart>();
		queryWrapper.eq("user_id", cart.getUserId()).eq("item_id", cart.getItemId());
		// 数据数据DB 主键Id
		Cart cartDB = cartMapper.selectOne(queryWrapper);
		if (cartDB == null) {
			// 用户第一次新增
			cart.setCreated(new Date())
			 	.setUpdated(cart.getCreated());
			cartMapper.insert(cart);
		} else {
			// 用户不是第一次新增该商品
			int num = cartDB.getNum() + cart.getNum();
			cartDB.setNum(num);
			cartDB.setUpdated(new Date());
			//SQL:update tb_cart set 所有的字段
			cartMapper.updateById(cartDB);
		} 
	}

	/**
	 * sql: update tb_cart set num = #{num},
	 * 		updated = #{updated}
	 * 		where 
	 * 		user_id=#{userId} 
	 * 		and
	 * 		item_id=#{itemId}
	 * 
	 * entity:要修改的数据
	 * {@link UpdateWrapper}  修改条件构造器
	 */
	@Override
	public void updateCartNum(Cart cart) {
		Cart cartTemp = new Cart();
		//cartTemp.setNum(cart.getNum())
		//		.setUpdated(new Date());
		UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<Cart>();
		updateWrapper.eq("item_id", cart.getItemId())
					 .eq("user_id", cart.getUserId());
		cartMapper.update(cartTemp, updateWrapper);
	}

}