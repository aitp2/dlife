package com.aitp.dlife.pinfan.repository.search;

import com.aitp.dlife.pinfan.domain.Attendee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Attendee entity.
 */
public interface AttendeeSearchRepository extends ElasticsearchRepository<Attendee, Long> {
}
