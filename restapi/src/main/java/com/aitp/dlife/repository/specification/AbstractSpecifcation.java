package com.aitp.dlife.repository.specification;

import java.util.List;

import com.aitp.dlife.service.dto.QueryDTO;

public abstract class AbstractSpecifcation {

	 List<QueryDTO>  querys;

	 
	 
	 
	public AbstractSpecifcation() {
		super();
	}

	public AbstractSpecifcation(List<QueryDTO> querys) {
		super();
		this.querys = querys;
	}

	public List<QueryDTO> getQuerys() {
		return querys;
	}

	public void setQuerys(List<QueryDTO> querys) {
		this.querys = querys;
	}
	
	 
	 
	
}
