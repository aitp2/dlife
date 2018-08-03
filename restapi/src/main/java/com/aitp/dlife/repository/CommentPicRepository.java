package com.aitp.dlife.repository;

import com.aitp.dlife.domain.CommentPic;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CommentPic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentPicRepository extends JpaRepository<CommentPic, Long> {

}
