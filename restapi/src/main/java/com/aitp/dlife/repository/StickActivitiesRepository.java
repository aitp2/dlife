package com.aitp.dlife.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.aitp.dlife.response.StickActivitiesResponse;

/**
 * Spring Data JPA repository for the ActivityParticipation entity.
 */
@Repository
public class StickActivitiesRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public List<StickActivitiesResponse> getStickActivities() {
		final String sql = "select pinFanActivity.id as id,"
					+ "'PIN' as channel,"
					+ "(select oss_path from pin_fan_activity left join pinfan_pics on pin_fan_activity.id = pinfan_pics.pin_fan_activity_id LIMIT 1) AS url "
					+ "from pin_fan_activity pinFanActivity "
					+ "where pinFanActivity.stick =1 order by pinFanActivity.stick_order asc";
		Session session = entityManager.unwrap(org.hibernate.Session.class);
		NativeQuery query = session.createNativeQuery(sql);
		query.addScalar("channel", StandardBasicTypes.STRING);
		query.addScalar("id", StandardBasicTypes.STRING);
		query.addScalar("url", StandardBasicTypes.STRING);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(StickActivitiesResponse.class));
		return query.getResultList();
	}

}
