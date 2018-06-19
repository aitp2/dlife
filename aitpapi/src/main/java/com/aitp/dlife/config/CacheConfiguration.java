package com.aitp.dlife.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.aitp.dlife.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.aitp.dlife.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.WechatUser.class.getName(), jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.Follow.class.getName(), jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.Recipe.class.getName(), jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.Recipe.class.getName() + ".recipeOrders", jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.Recipe.class.getName() + ".images", jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.Image.class.getName(), jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.RecipeOrder.class.getName(), jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.RecipeOrder.class.getName() + ".evaluats", jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.Evaluate.class.getName(), jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.Evaluate.class.getName() + ".images", jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.PinFanActivity.class.getName(), jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.PinFanActivity.class.getName() + ".attendees", jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.PinFanActivity.class.getName() + ".rinfanPics", jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.PinFanActivity.class.getName() + ".rates", jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.Attendee.class.getName(), jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.Rates.class.getName(), jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.Rates.class.getName() + ".rinfanPics", jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.PinfanPics.class.getName(), jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.FitnessActivity.class.getName(), jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.FitnessActivity.class.getName() + ".activityParticipations", jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.FitnessActivity.class.getName() + ".images", jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.ActivityParticipation.class.getName(), jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.ActivityParticipation.class.getName() + ".clockIns", jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.ClockIn.class.getName(), jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.ClockIn.class.getName() + ".pics", jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.Pics.class.getName(), jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.ClockinSummary.class.getName(), jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.PinFanActivity.class.getName() + ".pinfanPics", jcacheConfiguration);
            cm.createCache(com.aitp.dlife.domain.Rates.class.getName() + ".pinfanPics", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
