package com.aitp.dlife.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.aitp.dlife.domain.UserPointDetails;


/**
 * Spring Data  repository for the UserPointDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserPointDetailsRepository extends JpaRepository<UserPointDetails, Long> {

	Page<UserPointDetails> findAllByUserid(String userid, Pageable pageable);

}
