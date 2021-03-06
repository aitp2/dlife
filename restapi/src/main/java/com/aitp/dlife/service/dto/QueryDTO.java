package com.aitp.dlife.service.dto;


import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class QueryDTO {
	
	
	private String queryKey ;
	
	private Object queryValue;
	
	@JsonIgnore
	private String predicate;

	
	
	public QueryDTO() {
		super();
	}


	


	public QueryDTO(String queryKey, Object queryValue) {
		super();
		this.queryKey = queryKey;
		this.queryValue = queryValue;
		this.predicate = "and";
	}



	public QueryDTO(String queryKey, Object queryValue, String predicate) {
		super();
		this.queryKey = queryKey;
		this.queryValue = queryValue;
		this.predicate = predicate;
	}

	public String getQueryKey() {
		return queryKey;
	}

	public void setQueryKey(String queryKey) {
		this.queryKey = queryKey;
	}


	public Object getQueryValue() {
		return queryValue;
	}




	public void setQueryValue(Object queryValue) {
		this.queryValue = queryValue;
	}




	public String getPredicate() {
		return predicate;
	}

	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}

	public boolean isEmpty(){
	 	return ObjectUtils.isEmpty(this.getQueryValue());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((predicate == null) ? 0 : predicate.hashCode());
		result = prime * result + ((queryKey == null) ? 0 : queryKey.hashCode());
		result = prime * result + ((queryValue == null) ? 0 : queryValue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QueryDTO other = (QueryDTO) obj;
		if (predicate == null) {
			if (other.predicate != null)
				return false;
		} else if (!predicate.equals(other.predicate))
			return false;
		if (queryKey == null) {
			if (other.queryKey != null)
				return false;
		} else if (!queryKey.equals(other.queryKey))
			return false;
		if (queryValue == null) {
			if (other.queryValue != null)
				return false;
		} else if (!queryValue.equals(other.queryValue))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "QueryDTO [queryKey=" + queryKey + ", queryValue=" + queryValue + ", predicate=" + predicate + "]";
	}

	
	
	
}
