package com.example.lockzookeeper;

import com.example.lockzookeeper.util.ZKClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class LockZookeeperApplicationTests {

    @Autowired
    ZKClient client;

    @Test
    void testLock() throws Exception {

        log.info("上锁");
        InterProcessLock lock =  client.getInterProcessLock("/test-lock");
        lock.acquire();

        //debug 查看锁
        Thread.sleep(1000);
        if(lock.isAcquiredInThisProcess()){
            log.info("解锁");
            lock.release();
        }
    }

}
