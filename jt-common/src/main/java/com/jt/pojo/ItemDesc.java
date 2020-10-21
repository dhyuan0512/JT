package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("tb_item_desc")
public class ItemDesc extends BasePojo {
	private static final long serialVersionUID = 1016593866051306734L;
	//item表id与itemDesc表Id一致的
	@TableId //只表示主键不表示关系
	private Long itemId;
	private String itemDesc;

}
