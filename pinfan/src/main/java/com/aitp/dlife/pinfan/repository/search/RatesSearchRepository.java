package com.aitp.dlife.pinfan.repository.search;

import com.aitp.dlife.pinfan.domain.Rates;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Rates entity.
 */
public interface RatesSearchRepository extends ElasticsearchRepository<Rates, Long> {
}
