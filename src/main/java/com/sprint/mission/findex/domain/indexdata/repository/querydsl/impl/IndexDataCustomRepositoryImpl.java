package com.sprint.mission.findex.domain.indexdata.repository.querydsl.impl;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sprint.mission.findex.domain.indexdata.dto.IndexDataListRequest;
import com.sprint.mission.findex.domain.indexdata.entity.IndexData;
import com.sprint.mission.findex.domain.indexdata.entity.QIndexData;
import com.sprint.mission.findex.domain.indexdata.repository.querydsl.IndexDataCustomRepository;
import com.sprint.mission.findex.global.common.dto.CursorPageResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class IndexDataCustomRepositoryImpl implements IndexDataCustomRepository {

  private final JPAQueryFactory queryFactory;
  private final QIndexData indexData = QIndexData.indexData;

  @Override
  public CursorPageResponse<IndexData> findAll(IndexDataListRequest request) {
    int size = (request.size() != null && request.size() > 0) ? request.size() : 10;

    List<IndexData> content = queryFactory
        .selectFrom(indexData)
        .where(
            eqIndexInfoId(request.indexInfoId()),
            goeStartDate(request.startDate()),
            loeEndDate(request.endDate()),
            cursorCondition(request.idAfter())
        )
        .orderBy(getOrderSpecifier(request.sortField(), request.sortDirection()))
        .limit(size + 1)
        .fetch();

    boolean hasNext = content.size() > size;
    if (hasNext) {
      content = content.subList(0, size);
    }

    UUID nextCursor = null;
    UUID nextIdAfter = null;
    if (hasNext && !content.isEmpty()) {
      IndexData last = content.get(content.size() - 1);
      nextCursor = last.getId();
      nextIdAfter = last.getId();
    }

    Long totalElements = queryFactory
        .select(indexData.count())
        .from(indexData)
        .where(
            eqIndexInfoId(request.indexInfoId()),
            goeStartDate(request.startDate()),
            loeEndDate(request.endDate())
        )
        .fetchOne();

    return CursorPageResponse.of(content, nextCursor, nextIdAfter, size, totalElements, hasNext);
  }

  private BooleanExpression eqIndexInfoId(UUID indexInfoId) {
    return indexInfoId != null ? indexData.indexInfo.id.eq(indexInfoId) : null;
  }

  private BooleanExpression goeStartDate(LocalDate startDate) {
    return startDate != null ? indexData.baseDate.goe(startDate) : null;
  }

  private BooleanExpression loeEndDate(LocalDate endDate) {
    return endDate != null ? indexData.baseDate.loe(endDate) : null;
  }

  private BooleanExpression cursorCondition(UUID idAfter) {
    return idAfter != null ? indexData.id.gt(idAfter) : null;
  }

  private OrderSpecifier<?> getOrderSpecifier(String sortField, String sortDirection) {
    boolean isAsc = !"desc".equalsIgnoreCase(sortDirection);
    return switch (sortField != null ? sortField : "baseDate") {
      case "marketPrice" -> isAsc ? indexData.marketPrice.asc() : indexData.marketPrice.desc();
      case "closingPrice" -> isAsc ? indexData.closingPrice.asc() : indexData.closingPrice.desc();
      case "highPrice" -> isAsc ? indexData.highPrice.asc() : indexData.highPrice.desc();
      case "lowPrice" -> isAsc ? indexData.lowPrice.asc() : indexData.lowPrice.desc();
      case "versus" -> isAsc ? indexData.versus.asc() : indexData.versus.desc();
      case "fluctuationRate" -> isAsc ? indexData.fluctuationRate.asc() : indexData.fluctuationRate.desc();
      case "tradingQuantity" -> isAsc ? indexData.tradingQuantity.asc() : indexData.tradingQuantity.desc();
      case "tradingPrice" -> isAsc ? indexData.tradingPrice.asc() : indexData.tradingPrice.desc();
      case "marketTotalAmount" -> isAsc ? indexData.marketTotalAmount.asc() : indexData.marketTotalAmount.desc();
      default -> isAsc ? indexData.baseDate.asc() : indexData.baseDate.desc();
    };
  }
}