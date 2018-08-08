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

public class CommentSpecification implements Specification<Comment>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private List<QueryDTO>  querys;
	
	
	

	public CommentSpecification(List<QueryDTO> querys) {
		super();
		this.querys = querys;
	}

	/**
	 * query list
	 */
	@Override
	public Predicate toPredicate(Root<Comment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Path<String> idPath = root.get("id");
        QueryDTO id = querys.stream().filter(quz->quz.getQueryKey().equals("id")).findFirst().orElse(null);
        Predicate p = null;
        if(id!=null){
        	   p = criteriaBuilder.equal(idPath, id.getQueryValue());
        }
        
        return p;  
          

	}

}
