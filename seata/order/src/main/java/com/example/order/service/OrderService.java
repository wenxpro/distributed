package com.example.order.service;

import com.example.order.feign.AccountFeignClient;
import com.example.order.feign.StorageFeignClient;
import com.example.order.model.Order;
import com.example.order.repository.OrderDAO;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class OrderService {

    @Resource
    private AccountFeignClient accountFeignClient;
    @Resource
    private StorageFeignClient storageFeignClient;
    @Resource
    private OrderDAO orderDAO;


    /**
     * 下单：创建订单、减库存，涉及到两个服务
     *
     * @param userId
     * @param commodityCode
     * @param count
     */
    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    public void placeOrder(String userId, String commodityCode, Integer count) {
        BigDecimal orderMoney = new BigDecimal(count).multiply(new BigDecimal(5));
        Order order = new Order()
                .setUserId(userId)
                .setCommodityCode(commodityCode)
                .setCount(count)
                .setMoney(orderMoney);
        orderDAO.insert(order);
        storageFeignClient.deduct(commodityCode, count);

    }


    @Transactional(rollbackFor = Exception.class)
    public void create(String userId, String commodityCode, Integer count) {

        BigDecimal orderMoney = new BigDecimal(count).multiply(new BigDecimal(5));

        Order order = new Order()
                .setUserId(userId)
                .setCommodityCode(commodityCode)
                .setCount(count)
                .setMoney(orderMoney);
        orderDAO.insert(order);

        accountFeignClient.reduce(userId, orderMoney);

    }

}
