package com.aitp.dlife.uaa.repository.search;

import com.aitp.dlife.uaa.domain.WechatUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the WechatUser entity.
 */
public interface WechatUserSearchRepository extends ElasticsearchRepository<WechatUser, Long> {
}
