package com.aitp.dlife.repository;

import com.aitp.dlife.domain.CommentPic;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommentPic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentPicRepository extends JpaRepository<CommentPic, Long> {

}
