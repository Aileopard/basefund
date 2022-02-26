package com.leo.fundservice.mapper;


import com.leo.fundservice.model.FundHisInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 功能描述：基金历史明细mapper
 * @author leo-zu
 * @date 2021/08/08
 */
@Mapper
@Component
public interface FundHisInfoMapper {
    int insert(FundHisInfo record);

    int insertSelective(FundHisInfo record);

    int insertBatchFundHisInfo(@Param("list") List<FundHisInfo> list);
}