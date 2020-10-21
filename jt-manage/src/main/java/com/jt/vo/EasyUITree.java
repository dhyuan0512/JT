package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class EasyUITree {
	
	private Long id;  //分类id号
	private String text; //分类名称 文本信息
	private String state; //open 节点打开 closed关闭
	
}
