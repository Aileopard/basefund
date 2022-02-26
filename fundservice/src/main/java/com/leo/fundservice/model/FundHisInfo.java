package com.leo.fundservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundHisInfo implements Serializable {
    private String fundCode;

    private String fundName;

    private BigDecimal fundNetValue;

    private BigDecimal fundTotalNetValue;

    private BigDecimal dayIncreaseValue;

    private BigDecimal dayIncreaseRate;

    private Date entroDate;

    private static final long serialVersionUID = 1L;
}