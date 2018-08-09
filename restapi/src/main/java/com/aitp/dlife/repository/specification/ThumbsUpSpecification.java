package com.aitp.dlife.repository.specification;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.aitp.dlife.domain.Comment;
import com.aitp.dlife.domain.ThumbsUp;
import com.aitp.dlife.service.dto.QueryDTO;

public class ThumbsUpSpecification implements Specification<ThumbsUp>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private List<QueryDTO>  querys;
	
	
	

	public ThumbsUpSpecification(List<QueryDTO> querys) {
		super();
		this.querys = querys;
	}

	/**
	 * query list
	 */
	@Override
	public Predicate toPredicate(Root<ThumbsUp> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Path<String> keyName_1 = root.get("keyName_1");
        QueryDTO objectId = querys.stream().filter(quz->quz.getQueryKey().equals("objectId")).findFirst().orElse(null);
        Predicate p = null;
        if(objectId!=null){
        	   p = criteriaBuilder.equal(keyName_1, objectId.getQueryValue());
        }
        
        return p;  
          

	}

}
