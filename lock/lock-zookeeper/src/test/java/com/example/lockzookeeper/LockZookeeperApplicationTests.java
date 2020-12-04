package com.example.lockzookeeper;

import com.example.lockzookeeper.util.ZKlock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class LockZookeeperApplicationTests {

    @Autowired
    ZKlock zKlock;

    @Test
    void contextLoads() {
        zKlock.lock();
        log.info("上锁了");
        zKlock.unlock();
        log.info("解锁了");
    }

}
