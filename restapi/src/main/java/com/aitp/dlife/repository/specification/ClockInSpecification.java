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
import com.aitp.dlife.web.rest.vm.ClockInVM;

public class ClockInSpecification extends AbstractSpecifcation<ClockInVM> implements Query<Specification<ClockIn>> ,Specification<ClockIn>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

		public ClockInSpecification(Long wechatUserId, Long activityParticipationId, Long activityId) {
		super(new ClockInVM(wechatUserId,activityParticipationId,activityId));
	}
	/**
	 * query list
	 */
	@Override
	public Predicate toPredicate(Root<ClockIn> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
   
        List<Predicate> andPrediCate =new ArrayList<Predicate>();
        if(!ObjectUtils.isEmpty(querys.getWechatUserId())){
            Path<String> wechatUserId = root.join("activityParticipation").get("wechatUserId");
            andPrediCate.add(criteriaBuilder.equal(wechatUserId,querys.getWechatUserId()));
        }
        if(!ObjectUtils.isEmpty(querys.getActivityParticipationId())){
        	  Path<String> activityParticipationId = root.get("activityParticipation").get("id");
        	  andPrediCate.add(criteriaBuilder.equal(activityParticipationId, querys.getActivityParticipationId()));
        }
        if(!ObjectUtils.isEmpty(querys.getActivityId())){
        	  Path<String> activityIdNode = root.get("activityId");
        	  andPrediCate.add(criteriaBuilder.equal(activityIdNode, querys.getActivityId()));
        }
        return criteriaBuilder.and(andPrediCate.toArray(new Predicate[andPrediCate.size()]));  
          

	}
	@Override
	public Specification<ClockIn> Convert() {
		
		return (Specification<ClockIn>)this;
	}

}
