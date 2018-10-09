package com.aitp.dlife.repository;

import com.aitp.dlife.domain.Follow;
import com.aitp.dlife.domain.RecipeOrder;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the Follow entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

	Page<Follow> findAllByFollowUserId( Pageable pageable, String wechatUserId);

	Page<Follow> findAllByFollowedUserId( Pageable pageable, String wechatUserId);

    @Query(value = "SELECT * FROM Follow AS f where f.follow_user_id=:followUserId and f.followed_user_id=:followedUserId order by f.modify_time desc",nativeQuery = true)
    List<Follow> findByFollowUserIdAndFollowedUserId(@Param("followUserId")String followUserId, @Param("followedUserId")String followedUserId);

    @Query(value = "SELECT * FROM Follow AS f where f.followed_user_id=:followedUserId order by f.modify_time desc",nativeQuery = true)
    List<Follow> findAllByFollowedUserId(@Param("followedUserId")String followedUserId);

}
