package com.sprint.mission.findex.domain.dashboard.controller.api;

import com.sprint.mission.findex.domain.dashboard.dto.IndexChartPeriodType;
import com.sprint.mission.findex.domain.dashboard.dto.IndexChartResponse;
import com.sprint.mission.findex.domain.dashboard.dto.IndexPerformancePeriodType;
import com.sprint.mission.findex.domain.dashboard.dto.IndexPerformanceResponse;
import com.sprint.mission.findex.domain.dashboard.dto.RankedIndexPerformanceQueryCondition;
import com.sprint.mission.findex.domain.dashboard.dto.RankedIndexPerformanceResponse;
import com.sprint.mission.findex.global.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "지수 데이터 API")
public interface DashboardApi {

  @Operation(summary = "지수 성과 랭킹 조회", description = "지수의 성과 분석 랭킹을 조회합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "성과 랭킹 조회 성공",
          content = @Content(array = @ArraySchema(schema = @Schema(implementation = RankedIndexPerformanceResponse.class)))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 (유효하지 않은 기간 유형 등)",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "서버 오류",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  ResponseEntity<List<RankedIndexPerformanceResponse>> getIndexPerformanceRank(
      @ParameterObject @ModelAttribute @Valid RankedIndexPerformanceQueryCondition condition
  );

  @Operation(summary = "지수 차트 조회", description = "지수의 차트 데이터를 조회합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "차트 데이터 조회 성공",
          content = @Content(schema = @Schema(implementation = IndexChartResponse.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 (유효하지 않은 기간 유형 등)",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "404", description = "지수 정보를 찾을 수 없음",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "서버 오류",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  ResponseEntity<IndexChartResponse> getIndexChart(
      @Parameter(description = "지수 정보 ID", schema = @Schema(type = "string", format = "uuid"))
      @PathVariable UUID id,
      @Parameter(description = "차트 기간 유형 (MONTHLY, QUARTERLY, YEARLY)")
      @RequestParam(name = "periodType", defaultValue = "MONTHLY") IndexChartPeriodType periodType
  );

  @Operation(summary = "관심 지수 성과 조회", description = "즐겨찾기로 등록된 지수들의 성과를 조회합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "관심 지수 성과 조회 성공",
          content = @Content(array = @ArraySchema(schema = @Schema(implementation = IndexPerformanceResponse.class)))),
      @ApiResponse(responseCode = "500", description = "서버 오류",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  ResponseEntity<List<IndexPerformanceResponse>> getFavoriteIndexPerformance(
      @Parameter(description = "성과 기간 유형 (DAILY, WEEKLY, MONTHLY)")
      @RequestParam(defaultValue = "DAILY") IndexPerformancePeriodType periodType
  );
}