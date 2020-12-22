package com.example.txredission;

import com.alibaba.fastjson.JSON;
import com.example.txredission.consts.error.AsyncExceptionConsts;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RMap;
import org.redisson.api.RTransaction;
import org.redisson.api.RedissonClient;
import org.redisson.api.TransactionOptions;
import org.redisson.spring.transaction.RedissonTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;
import com.example.txredission.exception.TxException;

@SpringBootTest
@Slf4j
class TxRedissionApplicationTests {

    @Autowired
    private RedissonTransactionManager transactionManager;
    @Autowired
    RedissonClient redissionClient;

    @Test
    void test() {
        RMap<String, Object> map = redissionClient.getMap("anyMap");
        Object currentObject = map.putIfAbsent("test", "test");
        log.debug("currentObject:{}", currentObject);
        show();
    }

    @Test
    void testTx() {
        RTransaction transaction = redissionClient.createTransaction(TransactionOptions.defaults());
        RMap<String, Object> map = transaction.getMap("anyMap");
        Object currentObject = map.putIfAbsent("testTx", "testTx");
        transaction.commit();
    }

    @Test
    @Transactional
    void testAnnoTx() {
        RTransaction transaction = transactionManager.getCurrentTransaction();
        RMap<String, Object> map = transaction.getMap("anyMap");
        Object currentObject = map.putIfAbsent("testAnnoTx", "testAnnoTx");
    }

    @Test
    void testTx1234() throws TransactionException {
        RTransaction transaction = redissionClient.createTransaction(TransactionOptions.defaults());
        try {
            RMap<String, Object> map = transaction.getMap("anyMap");
            Object currentObject = map.putIfAbsent("testTx1234", "testTx1234");
            transaction.commit();
            throw new TxException(AsyncExceptionConsts.testException);
        } catch (TransactionException e) {
            log.info("testTx1234 报错了 开始回滚");
            transaction.rollback();
        }
    }


    @Test
    void show() {
        RMap<String, Object> map = redissionClient.getMap("anyMap");
        log.info("map:{}", JSON.toJSONString(map));
    }

}
