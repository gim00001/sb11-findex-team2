package com.sprint.mission.findex.domain.indexdata.dto;

import java.time.LocalDate;
import java.util.UUID;

public record IndexDataQueryCondition(
    UUID indexInfoId,
    LocalDate startDate,
    LocalDate endDate,
    UUID idAfter,
    String cursor,
    String sortField,
    String sortDirection,
    Integer size
) {
  public IndexDataQueryCondition {
    if (sortField == null) sortField = "baseDate";
    if (sortDirection == null) sortDirection = "desc";
    if (size == null) size = 10;
  }
}