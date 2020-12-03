package com.example.lockredission;

import com.example.lockredission.util.RedissonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class LockRedissionApplicationTests {
    @Autowired
    RedissonUtil redissonUtil;

    @Test
    void contextLoads() {
        RLock lock = redissonUtil.getRLock("test-redission");
        try {
            lock.lock();
            // 业务代码
            log.info("进入业务代码" );
        } catch (Exception e) {
            log.error("", e);
        }finally{
            lock.unlock();
        }

    }

}
