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

import com.aitp.dlife.domain.ClockIn;
import com.aitp.dlife.service.dto.QueryDTO;

public class ClockInSpecification extends AbstractSpecifcation implements Specification<ClockIn>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

	public ClockInSpecification(List<QueryDTO> querys) {
		super(querys);
	}

	/**
	 * query list
	 */
	@Override
	public Predicate toPredicate(Root<ClockIn> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
   
        QueryDTO wechatUserQuery = querys.stream().filter(quz->quz.getQueryKey().equals("wechatUserId")).findFirst().orElse(null);
        List<Predicate> andPrediCate =new ArrayList<Predicate>();
        if(!ObjectUtils.isEmpty(wechatUserQuery)&&!wechatUserQuery.isEmpty()){
            Path<String> wechatUserId = root.join("activityParticipation").get("wechatUserId");
            andPrediCate.add(criteriaBuilder.equal(wechatUserId, wechatUserQuery.getQueryValue()));
        }
        QueryDTO activityParticipationIdQuery = querys.stream().filter(quz->quz.getQueryKey().equals("activityParticipationId")).findFirst().orElse(null);
        if(!ObjectUtils.isEmpty(wechatUserQuery)&&!activityParticipationIdQuery.isEmpty()){
        	  Path<String> activityParticipationId = root.get("activityParticipation").get("id");
        	  andPrediCate.add(criteriaBuilder.equal(activityParticipationId, activityParticipationIdQuery.getQueryValue()));
        }
        QueryDTO activityId = querys.stream().filter(quz->quz.getQueryKey().equals("activityId")).findFirst().orElse(null);
        if(!ObjectUtils.isEmpty(wechatUserQuery)&&!activityId.isEmpty()){
        	  Path<String> activityIdNode = root.get("activityId");
        	  andPrediCate.add(criteriaBuilder.equal(activityIdNode, activityId.getQueryValue()));
        }
        return criteriaBuilder.and(andPrediCate.toArray(new Predicate[andPrediCate.size()]));  
          

	}

}
