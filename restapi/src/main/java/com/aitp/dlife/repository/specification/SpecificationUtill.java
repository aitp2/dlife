package com.aitp.dlife.repository.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.aitp.dlife.service.dto.QueryDTO;

public class SpecificationUtill {

//	
//	public static <T, V> Predicate SqlQueryMappingConvert(QueryDTO<V> query,Root<T> root,CriteriaBuilder criteWriaBuilder){
//		Predicate predicate = null;
//		 Path<Object> Path = root.get(query.getQueryKey());
//		switch (query.getPredicate()) {
//		case "eq":
//		  predicate  = criteriaBuilder.equal(Path, query.getQueryValue());
//			break;
//		case "lt":
//			  predicate  = criteriaBuilder.lt(Path, query.getQueryValue());
//		default:
//			break;
//		}
//		return predicate;
//	}
//	
}
