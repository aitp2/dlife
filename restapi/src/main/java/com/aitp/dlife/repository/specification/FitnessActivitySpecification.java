package com.aitp.dlife.repository.specification;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import com.aitp.dlife.domain.FitnessActivity;
import com.aitp.dlife.service.dto.QueryDTO;
import com.aitp.dlife.service.enums.Status;
import com.aitp.dlife.web.rest.errors.CustomParameterizedException;

public class FitnessActivitySpecification extends AbstractSpecifcation implements Specification<FitnessActivity>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

	public FitnessActivitySpecification(List<QueryDTO> querys) {
		super(querys);
	}

	/**
	 * query list
	 */
	@Override
	public Predicate toPredicate(Root<FitnessActivity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
   
        QueryDTO wechatUserQuery = querys.stream().filter(quz->quz.getQueryKey().equals("wechatUserId")).findFirst().orElse(null);
        List<Predicate> andPrediCate =new ArrayList<Predicate>();
        if(!ObjectUtils.isEmpty(wechatUserQuery)&&!wechatUserQuery.isEmpty()){
            Path<String> wechatUserId = root.join("activityParticipations").get("wechatUserId");
            andPrediCate.add(criteriaBuilder.equal(wechatUserId, wechatUserQuery.getQueryValue()));
        }
        QueryDTO status = querys.stream().filter(quz->quz.getQueryKey().equals("status")).findFirst().orElse(null);
        if(!ObjectUtils.isEmpty(status)&&!status.isEmpty()){
        	  Expression<String> activityStartTime = root.get("activityStartTime").as(String.class);
        	  Expression<String> activityEndTime = root.get("activityEndTime").as(String.class);
        	  SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		  String nowDateString =   sdf.format(new Date());
        	  switch (Status.prease(Integer.valueOf(status.getQueryValue().toString()))) {
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
				throw new CustomParameterizedException("not have the status "+status.getQueryValue()+" code.","status");
			}
        }
     
        return criteriaBuilder.and(andPrediCate.toArray(new Predicate[andPrediCate.size()]));  
          

	}

}
