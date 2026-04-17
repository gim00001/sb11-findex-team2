package com.sprint.mission.findex.domain.indexdata.service;

import com.sprint.mission.findex.domain.indexdata.dto.IndexDataCreateRequest;
import com.sprint.mission.findex.domain.indexdata.dto.IndexDataResponse;
import com.sprint.mission.findex.domain.indexdata.dto.IndexDataUpdateRequest;
import com.sprint.mission.findex.domain.indexdata.entity.IndexData;
import com.sprint.mission.findex.domain.indexdata.mapper.IndexDataMapper;
import com.sprint.mission.findex.domain.indexdata.repository.IndexDataRepository;
import com.sprint.mission.findex.domain.indexinfo.entity.IndexInfo;
import com.sprint.mission.findex.domain.indexinfo.entity.SourceType;

import com.sprint.mission.findex.domain.indexinfo.repository.IndexInfoRepository;
import com.sprint.mission.findex.global.exception.ApiException;
import com.sprint.mission.findex.global.exception.ApiException.ERROR;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Stream;
import java.io.PrintWriter;
import java.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
import com.sprint.mission.findex.domain.indexdata.dto.IndexDataExportRequest;

@Service
@RequiredArgsConstructor
public class IndexDataService {

  private final IndexDataRepository indexDataRepository;
  private final IndexInfoRepository indexInfoRepository;
  private final IndexDataMapper indexDataMapper;

  @Transactional
  public IndexDataResponse create(IndexDataCreateRequest request) {
    IndexInfo indexInfo = indexInfoRepository.findById(request.indexInfoId())
        .orElseThrow(() -> new ApiException(ERROR.INDEX_INFO_NOT_FOUND));

    if (indexDataRepository.existsByIndexInfoAndBaseDate(indexInfo, request.baseDate())) {
      throw new ApiException(ERROR.INDEX_DATA_DUPLICATED);
    }

    IndexData indexData = IndexData.builder()
        .indexInfo(indexInfo)
        .baseDate(request.baseDate())
        .sourceType(request.sourceType() != null ? request.sourceType() : SourceType.USER)
        .marketPrice(request.marketPrice())
        .closingPrice(request.closingPrice())
        .highPrice(request.highPrice())
        .lowPrice(request.lowPrice())
        .versus(request.versus())
        .fluctuationRate(request.fluctuationRate())
        .tradingQuantity(request.tradingQuantity())
        .tradingPrice(request.tradingPrice())
        .marketTotalAmount(request.marketTotalAmount())
        .build();

    try {
      return indexDataMapper.toResponse(indexDataRepository.save(indexData));
    } catch (DataIntegrityViolationException e) {
      throw new ApiException(ERROR.INDEX_DATA_DUPLICATED);
    }
  }

  @Transactional
  public IndexDataResponse update(UUID id, IndexDataUpdateRequest request) {
    IndexData indexData = indexDataRepository.findById(id)
        .orElseThrow(() -> new ApiException(ERROR.INDEX_DATA_NOT_FOUND));

    indexData.update(
        request.marketPrice(),
        request.closingPrice(),
        request.highPrice(),
        request.lowPrice(),
        request.versus(),
        request.fluctuationRate(),
        request.tradingQuantity(),
        request.tradingPrice(),
        request.marketTotalAmount()
    );

    return indexDataMapper.toResponse(indexData);
  }

  @Transactional
  public void delete(UUID id) {
    IndexData indexData = indexDataRepository.findById(id)
        .orElseThrow(() -> new ApiException(ERROR.INDEX_DATA_NOT_FOUND));

    indexDataRepository.delete(indexData);
  }

  @Transactional(readOnly = true)
  public void exportCsv(IndexDataExportRequest request, HttpServletResponse response)
    throws IOException {

    response.setContentType("text/csv");
    response.setCharacterEncoding("UTF-8");
    response.setHeader("Content-Disposition", "attachment; filename=index-data.csv");

    PrintWriter writer = response.getWriter();

    writer.println("id,indexInfoId,baseDate,sourceType,marketPrice,closingPrice," +
        "highPrice,lowPrice,versus,fluctuationRate,tradingQuantity," +
        "tradingPrice,marketTotalAmount");

    try (Stream<IndexData> stream = indexDataRepository.streamForExport(
        request.indexInfoId(),
        request.startDate(),
        request.endDate()
    )) {
      stream.forEach(data -> writer.println(String.join(",",
          data.getId().toString(),
          data.getIndexInfo().getId().toString(),
          data.getBaseDate().toString(),
          data.getSourceType().toString(),
          data.getMarketPrice().toString(),
          data.getClosingPrice().toString(),
          data.getHighPrice().toString(),
          data.getLowPrice().toString(),
          data.getVersus().toString(),
          data.getFluctuationRate().toString(),
          data.getTradingQuantity().toString(),
          data.getMarketTotalAmount().toString()
          )));
    }
    writer.flush();
  }
}
