package com.aitp.dlife.repository.specification;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.aitp.dlife.domain.Comment;
import com.aitp.dlife.service.dto.QueryDTO;

public class CommentSpecification extends AbstractSpecifcation implements Specification<Comment>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	

	public CommentSpecification(List<QueryDTO> querys) {
		super(querys);
	}

	/**
	 * query list
	 */
	@Override
	public Predicate toPredicate(Root<Comment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
      
        QueryDTO objectId = querys.stream().filter(quz->quz.getQueryKey().equals("objectId")).findFirst().orElse(null);
        QueryDTO channel = querys.stream().filter(quz->quz.getQueryKey().equals("channel")).findFirst().orElse(null);
        Predicate objectPredicate = null;
        Predicate channelPredicate = null;
        if(null!=objectId&&!objectId.isEmpty()){
        	Path<String> idPath = root.get("objectId");
        	objectPredicate = criteriaBuilder.equal(idPath, objectId.getQueryValue());
        }
        if(null!=channel&&!channel.isEmpty()){
        	Path<String> channelPath = root.get("channel");
        	channelPredicate = criteriaBuilder.equal(channelPath, channel.getQueryValue());
        }
        Predicate predicate = criteriaBuilder.and(objectPredicate,channelPredicate);
        return predicate;  
          

	}

}
