package com.sprint.mission.findex.domain.indexdata.dto;

import com.sprint.mission.findex.domain.indexdata.entity.IndexData;
import com.sprint.mission.findex.domain.indexinfo.entity.IndexInfo.SourceType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record IndexDataResponse (

    UUID id,
    UUID indexInfoId,
    LocalDate baseDate,
    SourceType sourceType,
    BigDecimal marketPrice,
    BigDecimal closingPrice,
    BigDecimal highPrice,
    BigDecimal lowPrice,
    BigDecimal versus,
    BigDecimal fluctuationRate,
    Long tradingQuantity,
    BigDecimal tradingPrice,
    BigDecimal marketTotalAmount
) {

  public static IndexDataResponse from(IndexData indexData) {
    return new IndexDataResponse(
        indexData.getId(),
        indexData.getIndexInfo().getId(),
        indexData.getBaseDate(),
        indexData.getSourceType(),
        indexData.getMarketPrice(),
        indexData.getClosingPrice(),
        indexData.getHighPrice(),
        indexData.getLowPrice(),
        indexData.getVersus(),
        indexData.getFluctuationRate(),
        indexData.getTradingQuantity(),
        indexData.getTradingPrice(),
        indexData.getMarketTotalAmount()
        );
  }
}
