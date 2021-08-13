package com.example.storage.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.storage.entity.Storage;
import com.example.storage.repository.StorageDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
public class StorageService {

    @Resource
    private StorageDAO storageDAO;

    /**
     * 减库存
     * 
     * @param commodityCode
     * @param count
     */
    @Transactional(rollbackFor = Exception.class)
    public void deduct(String commodityCode, int count) {
        if (commodityCode.equals("product-2")) {
            throw new RuntimeException("异常:模拟业务异常:Storage branch exception");
        }

        QueryWrapper<Storage> wrapper = new QueryWrapper<>();
        wrapper.setEntity(new Storage().setCommodityCode(commodityCode));
        Storage storage = storageDAO.selectOne(wrapper);
        storage.setCount(storage.getCount() - count);

        storageDAO.updateById(storage);
    }
}
