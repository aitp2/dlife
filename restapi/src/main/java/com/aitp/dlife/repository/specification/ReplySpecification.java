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

import com.aitp.dlife.domain.Comment;
import com.aitp.dlife.domain.enumeration.CommentChannel;
import com.aitp.dlife.domain.enumeration.CommentModule;
import com.aitp.dlife.web.rest.vm.ReplyVM;

public class ReplySpecification extends AbstractSpecifcation<ReplyVM> implements Specification<Comment>{


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;




	public ReplySpecification(CommentChannel channel, String wechatUserId, String praentId,CommentModule module) {
		super(new ReplyVM(channel, wechatUserId,praentId, module));
	}

	/**
	 * query list
	 */
	@Override
	public Predicate toPredicate(Root<Comment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        Path<Long> objectId = root.get("objectId");
        Predicate objectIdPredicate = criteriaBuilder.isNull(objectId);
        predicates.add(objectIdPredicate);
        if(!ObjectUtils.isEmpty(querys.getChannel())){
        	Path<CommentChannel> channelPath = root.get("channel");
            Predicate	channelPredicate = criteriaBuilder.equal(channelPath, querys.getChannel());
            predicates.add(channelPredicate);
        }
        if(!ObjectUtils.isEmpty(querys.getModel())){
        	Path<CommentModule> modelPath = root.get("module");
            Predicate	channelPredicate = criteriaBuilder.equal(modelPath, querys.getModel());
            predicates.add(channelPredicate);
        }
        if(!ObjectUtils.isEmpty(querys.getWechatUserId())){
            Path<Long> wechatUserIdPath = root.get("wechatUserId");
            Predicate wechatUserIdPredicate = criteriaBuilder.equal(wechatUserIdPath, querys.getWechatUserId());
            predicates.add(wechatUserIdPredicate);
        }
        if(!ObjectUtils.isEmpty(querys.getParentId())){
            Path<Long> parentId = root.get("parentId");
            Predicate parentIdPredicate = criteriaBuilder.equal(parentId, querys.getParentId());
            predicates.add(parentIdPredicate);
        }
        
        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        return predicate;


	}

}
