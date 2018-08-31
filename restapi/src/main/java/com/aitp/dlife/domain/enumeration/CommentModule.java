package com.aitp.dlife.domain.enumeration;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aitp.dlife.domain.Comment;
import com.aitp.dlife.repository.CommentRepository;

/**
 * The CommentModule enumeration.
 */
public enum CommentModule {
    COMMENT {
		@Override
		public CommentChannel submit(JpaRepository jpaRepository,Long id) {
			CommentRepository commentRepository  =  (CommentRepository) jpaRepository;	
		    Comment comment = commentRepository.getOne(id);
	        comment.setReply_count(comment.getReply_count() == null? 1:comment.getReply_count() + 1);
	        commentRepository.save(comment);
	        return comment.getChannel();
		}
	} , ACTIVITY {

		@Override
		public CommentChannel submit(JpaRepository jpaRepository, Long s) {
			return null;
			// TODO Auto-generated method stub
			
		}
	
	}, USERCENTER {

		@Override
		public CommentChannel submit(JpaRepository jpaRepository, Long s) {
			return null;
			// TODO Auto-generated method stub
			
		}
	
	};
	
	
	abstract CommentChannel submit(JpaRepository jpaRepository, Long s);


	
}
