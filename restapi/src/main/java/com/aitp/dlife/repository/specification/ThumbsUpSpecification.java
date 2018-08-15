package com.aitp.dlife.repository.specification;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import com.aitp.dlife.domain.ThumbsUp;
import com.aitp.dlife.web.rest.vm.ThumbsUpVM;

public class ThumbsUpSpecification extends AbstractSpecifcation<ThumbsUpVM> implements Specification<ThumbsUp>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public ThumbsUpSpecification(String objectId) {
		super(new ThumbsUpVM(objectId));
	}

	/**
	 * query list
	 */
	@Override
	public Predicate toPredicate(Root<ThumbsUp> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Path<String> keyName_1 = root.get("keyName_1");
        Predicate p = null;
        if(ObjectUtils.isEmpty(querys.getObjectId())){
        	   p = criteriaBuilder.equal(keyName_1, querys.getObjectId());
        }
        return p;  
          
	}

}
