package com.example.txredission;

import com.alibaba.fastjson.JSON;
import com.example.txredission.consts.error.AsyncExceptionConsts;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RMap;
import org.redisson.api.RTransaction;
import org.redisson.api.RedissonClient;
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
        Object currentObject = map.putIfAbsent("123", "test");
        log.debug("currentObject:{}",currentObject);
        show();
    }
    @Test
    @Transactional
    void testTx() throws TransactionException {
        RTransaction transaction = transactionManager.getCurrentTransaction();
        RMap<String, Object> map = transaction.getMap("anyMap");
        Object currentObject = map.putIfAbsent("321", "testTx");
        show();
//        throw new TxException(AsyncExceptionConsts.testException);
    }

    @Test
    void show(){
        RMap<String, Object> map = redissionClient.getMap("anyMap");
        log.info("map:{}", JSON.toJSONString(map));
    }

}
