package com.example.txredission.conf;

import cn.hutool.core.util.StrUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redission conf
 * @author wenx
 * @date 2020-12-02
 */
@Configuration
public class RedissionConfig {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public RedissonClient getRedisson(){

        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort());
        //版本默认auth 连接
        if(StrUtil.isNotEmpty(redisProperties.getPassword())){
            config.useSingleServer().setPassword(redisProperties.getPassword());
        }
        config.useSingleServer().setDatabase(0);
        return Redisson.create(config);
    }

}
