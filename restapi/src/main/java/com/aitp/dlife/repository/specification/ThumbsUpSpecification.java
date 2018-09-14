package com.aitp.dlife.repository.specification;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import com.aitp.dlife.domain.ThumbsUp;
import com.aitp.dlife.domain.enumeration.ThumbsUpModule;
import com.aitp.dlife.web.rest.vm.ThumbsUpVM;

public class ThumbsUpSpecification extends AbstractSpecifcation<ThumbsUpVM> implements Specification<ThumbsUp>{


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	public ThumbsUpSpecification(Long objectId,ThumbsUpModule thumbsUpModule) {
		super(new ThumbsUpVM(objectId,thumbsUpModule));
	}

	/**
	 * query list
	 */
	@Override
	public Predicate toPredicate(Root<ThumbsUp> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		  List<Predicate> predicates = new ArrayList<>();
        if(ObjectUtils.isEmpty(querys.getObjectId())){
        	  Path<String> keyName_1 = root.get("keyName_1");
        	  predicates.add(criteriaBuilder.equal(keyName_1, querys.getObjectId()));
        }
        if(ObjectUtils.isEmpty(querys.getModel())){
        	  Path<ThumbsUpModule> keyName_1 = root.get("module");
        	  predicates.add(criteriaBuilder.equal(keyName_1, querys.getModel()));
     }
        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        return predicate;
	}
}
