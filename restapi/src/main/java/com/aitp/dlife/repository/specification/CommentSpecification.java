package com.aitp.dlife.repository.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.aitp.dlife.domain.enumeration.CommentChannel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import com.aitp.dlife.domain.Comment;
import com.aitp.dlife.web.rest.vm.CommentVM;

import java.util.ArrayList;
import java.util.List;

public class CommentSpecification extends AbstractSpecifcation<CommentVM> implements Specification<Comment>{


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;




	public CommentSpecification(String objectId, CommentChannel channel, String wechatUserId) {
		super(new CommentVM(objectId, channel, wechatUserId));
	}

	/**
	 * query list
	 */
	@Override
	public Predicate toPredicate(Root<Comment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if(!ObjectUtils.isEmpty(querys.getObjectId())){
        	Path<String> idPath = root.get("objectId");
            Predicate objectPredicate = criteriaBuilder.equal(idPath, querys.getObjectId());
            predicates.add(objectPredicate);
        }
        if(!ObjectUtils.isEmpty(querys.getChannel())){
        	Path<CommentChannel> channelPath = root.get("channel");
            Predicate	channelPredicate = criteriaBuilder.equal(channelPath, querys.getChannel());
            predicates.add(channelPredicate);
        }
        if(!ObjectUtils.isEmpty(querys.getWechatUserId())){
            Path<CommentChannel> wechatUserIdPath = root.get("wechatUserId");
            Predicate wechatUserIdPredicate = criteriaBuilder.equal(wechatUserIdPath, querys.getWechatUserId());
            predicates.add(wechatUserIdPredicate);
        }
        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        return predicate;


	}

}
