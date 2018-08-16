//package com.aitp.dlife.repository.specification;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Expression;
//import javax.persistence.criteria.Path;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;
//
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.util.ObjectUtils;
//
//import com.aitp.dlife.domain.PinFanActivity;
//import com.aitp.dlife.service.dto.QueryDTO;
//
//public class PinFanActivitySpecification extends AbstractSpecifcation implements Specification<PinFanActivity> {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//
//	@Override
//	public Predicate toPredicate(Root<PinFanActivity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//		
//		
//        QueryDTO startTime = querys.stream().filter(quz->quz.getQueryKey().equals("startDate")).findFirst().orElse(null);
//        QueryDTO endTime = querys.stream().filter(quz->quz.getQueryKey().equals("endDate")).findFirst().orElse(null);
//        List<Predicate> andPrediCate =new ArrayList<Predicate>();
//        if(null!=startTime&&!startTime.isEmpty()){
//        	Expression<String> startPath = root.join("activityParticipations").get("startDate").as(String.class);
//                   andPrediCate.add(criteriaBuilder.greaterThanOrEqualTo(startPath, startTime.getQueryValue().toString()));
//        }
//        if(null!=endTime&&!endTime.isEmpty()){
//        	
//        }
//     
//        return criteriaBuilder.and();  
//	}
//
//}
