package com.sprint.mission.findex.domain.indexdata.dto;

import com.sprint.mission.findex.domain.indexinfo.entity.IndexInfo.SourceType;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record IndexDataCreateRequest (

  @NotNull
  UUID indexInfoId,

  @NotNull
  LocalDate baseDate,

  @NotNull
  SourceType sourceType,

  @NotNull
  BigDecimal marketPrice,

  @NotNull
  BigDecimal closingPrice,

  @NotNull
  BigDecimal highPrice,

  @NotNull
  BigDecimal lowPrice,

  @NotNull
  BigDecimal versus,

  @NotNull
  BigDecimal fluctuationRate,

  @NotNull
  Long tradingQuantity,

  @NotNull
  BigDecimal tradingPrice,

  @NotNull
  BigDecimal marketTotalAmount
) {}
