package com.leo.fundservice.service;



import com.leo.fundservice.model.FundHisInfo;

import java.util.List;

/**
 * @author leo-zu
 * @create 2021-05-28 18:47
 */
public interface FundHisInfoService {
    int insertBatchFundHisInfo(List<FundHisInfo> list);
}
