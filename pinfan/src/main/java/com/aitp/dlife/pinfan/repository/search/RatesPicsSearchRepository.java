package com.aitp.dlife.pinfan.repository.search;

import com.aitp.dlife.pinfan.domain.RatesPics;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RatesPics entity.
 */
public interface RatesPicsSearchRepository extends ElasticsearchRepository<RatesPics, Long> {
}
