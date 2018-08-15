package com.aitp.dlife.repository.specification;

public abstract class AbstractSpecifcation<T> {

	  T  querys;

	 
	public AbstractSpecifcation() {
		super();
	}



	public AbstractSpecifcation(T querys) {
		super();
		this.querys = querys;
	}




	public T getQuerys() {
		return querys;
	}

	 
	
}
