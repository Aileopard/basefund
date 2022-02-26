package com.leo.fundservice.service.impl;

import com.leo.fundservice.mapper.FundHisInfoMapper;
import com.leo.fundservice.model.FundHisInfo;
import com.leo.fundservice.service.FundHisInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author leo-zu
 * @create 2021-05-28 18:48
 */
@Service
@Slf4j
public class FundHisInfoServiceImpl implements FundHisInfoService {
    @Autowired
    private FundHisInfoMapper fundHisInfoMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertBatchFundHisInfo(List<FundHisInfo> list) {
        return fundHisInfoMapper.insertBatchFundHisInfo(list);
    }
}
