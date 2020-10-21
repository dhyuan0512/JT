package com.jt.vo;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
@Data
@Accessors(chain =true)
@NoArgsConstructor
@AllArgsConstructor
public class EasyUITable implements Serializable{
	
	private static final long serialVersionUID = 6820547337764515475L;

	private Integer total;
	 
	private List<?>  rows;
	 
}
