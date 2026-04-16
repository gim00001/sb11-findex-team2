package com.sprint.mission.findex.domain.indexdata.dto;

import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record IndexDataUpdateRequest(

    @PositiveOrZero
    BigDecimal marketPrice,

    @PositiveOrZero
    BigDecimal closingPrice,

    @PositiveOrZero
    BigDecimal highPrice,

    @PositiveOrZero
    BigDecimal lowPrice,

    BigDecimal versus,

    BigDecimal fluctuationRate,

    @PositiveOrZero
    Long tradingQuantity,

    @PositiveOrZero
    Long tradingPrice,

    @PositiveOrZero
    Long marketTotalAmount
) {}