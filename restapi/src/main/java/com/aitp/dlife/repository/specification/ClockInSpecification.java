package com.aitp.dlife.repository.specification;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.aitp.dlife.service.mapper.InstantMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import com.aitp.dlife.domain.ClockIn;
import com.aitp.dlife.web.rest.vm.ClockInVM;

public class ClockInSpecification extends AbstractSpecifcation<ClockInVM> implements Specification<ClockIn>{


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;



		public ClockInSpecification(Long wechatUserId, Long activityParticipationId, Long activityId, String startTime, String endTime) {
		super(new ClockInVM(wechatUserId,activityParticipationId,activityId,startTime,endTime));
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
        if (!ObjectUtils.isEmpty(querys.getStartTime())){
            Path<Instant> createTime = root.get("punchDateTime");
            andPrediCate.add(criteriaBuilder.greaterThanOrEqualTo(createTime,InstantMapper.fromString(querys.getStartTime())));
        }else {
            Path<Instant> createTime = root.get("punchDateTime");
            SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowDateString =   sdf.format(new Date());
            andPrediCate.add(criteriaBuilder.greaterThanOrEqualTo(createTime, InstantMapper.fromString(nowDateString)));
        }
        if (!ObjectUtils.isEmpty(querys.getEndTime())){
            Path<Instant> createTime = root.get("punchDateTime");
            andPrediCate.add(criteriaBuilder.lessThan(createTime,InstantMapper.fromString(querys.getEndTime())));
        }
        return criteriaBuilder.and(andPrediCate.toArray(new Predicate[andPrediCate.size()]));


	}

}
