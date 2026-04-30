package com.sprint.mission.findex.domain.dashboard.controller;

import com.sprint.mission.findex.domain.dashboard.controller.api.DashboardApi;
import com.sprint.mission.findex.domain.dashboard.dto.IndexChartPeriodType;
import com.sprint.mission.findex.domain.dashboard.dto.IndexChartResponse;
import com.sprint.mission.findex.domain.dashboard.dto.IndexPerformancePeriodType;
import com.sprint.mission.findex.domain.dashboard.dto.IndexPerformanceResponse;
import com.sprint.mission.findex.domain.dashboard.dto.RankedIndexPerformanceQueryCondition;
import com.sprint.mission.findex.domain.dashboard.dto.RankedIndexPerformanceResponse;
import com.sprint.mission.findex.domain.dashboard.service.DashboardChartService;
import com.sprint.mission.findex.domain.dashboard.service.DashboardFavoriteService;
import com.sprint.mission.findex.domain.dashboard.service.DashboardRankService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/index-data")
public class DashboardController implements DashboardApi {

    private final DashboardRankService dashboardRankService;
    private final DashboardChartService dashboardChartService;
    private final DashboardFavoriteService dashboardFavoriteService;

    @GetMapping("/performance/rank")
    public ResponseEntity<List<RankedIndexPerformanceResponse>> getIndexPerformanceRank(
        @ParameterObject @ModelAttribute @Valid RankedIndexPerformanceQueryCondition condition) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(dashboardRankService.getIndexPerformanceRank(condition));
    }

    @GetMapping("/{id}/chart")
    public ResponseEntity<IndexChartResponse> getIndexChart(
        @PathVariable UUID id,
        @RequestParam(name = "periodType", defaultValue = "MONTHLY") IndexChartPeriodType periodType
    ) {
        return ResponseEntity.ok(dashboardChartService.getIndexChart(id, periodType));
    }

    @GetMapping("/performance/favorite")
    public ResponseEntity<List<IndexPerformanceResponse>> getFavoriteIndexPerformance(
        @RequestParam(name = "periodType", defaultValue = "DAILY") IndexPerformancePeriodType periodType
    ) {
        return ResponseEntity.ok(dashboardFavoriteService.getFavoriteIndexPerformance(periodType));
    }
}