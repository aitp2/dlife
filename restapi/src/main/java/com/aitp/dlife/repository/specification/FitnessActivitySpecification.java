package com.aitp.dlife.repository.specification;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import com.aitp.dlife.domain.FitnessActivity;
import com.aitp.dlife.service.dto.QueryDTO;
import com.aitp.dlife.service.enums.Status;
import com.aitp.dlife.web.rest.errors.CustomParameterizedException;
import com.aitp.dlife.web.rest.vm.FitnessActivityVM;

public class FitnessActivitySpecification extends AbstractSpecifcation<FitnessActivityVM> implements Specification<FitnessActivity>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

	public FitnessActivitySpecification(Long wechatUserId, Integer status) {
		super(new FitnessActivityVM(wechatUserId,status));
	}

	/**
	 * query list
	 */
	@Override
	public Predicate toPredicate(Root<FitnessActivity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
   
        List<Predicate> andPrediCate =new ArrayList<Predicate>();
        if(!ObjectUtils.isEmpty(querys.getWechatUserId())){
            Path<String> wechatUserId = root.join("activityParticipations",JoinType.LEFT).get("wechatUserId");
            andPrediCate.add(criteriaBuilder.equal(wechatUserId, querys.getWechatUserId()));
        }
        if(!ObjectUtils.isEmpty(querys.getStatus())){
        	  Expression<String> activityStartTime = root.get("activityStartTime").as(String.class);
        	  Expression<String> activityEndTime = root.get("activityEndTime").as(String.class);
        	  SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		  String nowDateString =   sdf.format(new Date());
        	  switch (Status.prease(querys.getStatus())) {
			case OPEND:
				  andPrediCate.add(criteriaBuilder.greaterThanOrEqualTo(activityStartTime, nowDateString));
				break;
			case IN_PROGRESS:
				  andPrediCate.add(criteriaBuilder.lessThanOrEqualTo(activityStartTime, nowDateString));
				  andPrediCate.add(criteriaBuilder.greaterThanOrEqualTo(activityEndTime, nowDateString));
				break;
			case END:
				  andPrediCate.add(criteriaBuilder.lessThan(activityEndTime, nowDateString));
				break;
			default:
				throw new CustomParameterizedException("not have the status "+querys.getStatus()+" code.","status");
			}
        }
     
        return criteriaBuilder.and(andPrediCate.toArray(new Predicate[andPrediCate.size()]));  
          

	}

}
