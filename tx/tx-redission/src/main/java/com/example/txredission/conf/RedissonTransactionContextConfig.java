package com.example.txredission.conf;


import org.redisson.api.RedissonClient;
import org.redisson.spring.transaction.RedissonTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PreDestroy;

/**
 * redission 事务管理
 * @author wenx
 * @date 2020-12-03
 */
@Configuration
@EnableTransactionManagement
public class RedissonTransactionContextConfig {

    @Autowired
    RedissonClient redissonClient;

    @Bean
    public RedissonTransactionManager transactionManager(RedissonClient redisson) {
        return new RedissonTransactionManager(redisson);
    }

    @PreDestroy
    public void destroy() {
        redissonClient.shutdown();
    }
}
