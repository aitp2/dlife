package com.aitp.dlife.repository.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import com.aitp.dlife.domain.Comment;
import com.aitp.dlife.web.rest.vm.CommentVM;

public class CommentSpecification extends AbstractSpecifcation<CommentVM> implements Specification<Comment>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	

	public CommentSpecification(String objectId, String channel) {
		super(new CommentVM(objectId, channel));
	}

	/**
	 * query list
	 */
	@Override
	public Predicate toPredicate(Root<Comment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate objectPredicate = null;
        Predicate channelPredicate = null;
        if(ObjectUtils.isEmpty(querys.getObjectId())){
        	Path<String> idPath = root.get("objectId");
        	objectPredicate = criteriaBuilder.equal(idPath, querys.getObjectId());
        }
        if(ObjectUtils.isEmpty(querys.getChannel())){
        	Path<String> channelPath = root.get("channel");
        	channelPredicate = criteriaBuilder.equal(channelPath, querys.getChannel());
        }
        Predicate predicate = criteriaBuilder.and(objectPredicate,channelPredicate);
        return predicate;  
          

	}

}
