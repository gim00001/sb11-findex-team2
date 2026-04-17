package com.sprint.mission.findex.domain.indexdata.controller;

import com.sprint.mission.findex.domain.indexdata.dto.IndexDataCreateRequest;
import com.sprint.mission.findex.domain.indexdata.dto.IndexDataResponse;
import com.sprint.mission.findex.domain.indexdata.dto.IndexDataUpdateRequest;
import com.sprint.mission.findex.domain.indexdata.service.IndexDataService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sprint.mission.findex.domain.indexdata.dto.IndexDataExportRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@RestController
@RequestMapping("/api/index-data")
@RequiredArgsConstructor
public class IndexDataController {

  private final IndexDataService indexDataService;

  @PostMapping
  public ResponseEntity<IndexDataResponse> create(
      @Valid @RequestBody IndexDataCreateRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(indexDataService.create(request));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<IndexDataResponse> update(
      @PathVariable UUID id,
      @Valid @RequestBody IndexDataUpdateRequest request) {
    return ResponseEntity.ok(indexDataService.update(id, request));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    indexDataService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/export/csv")
  public void exportCsv(
      @ModelAttribute IndexDataExportRequest request,
      HttpServletResponse response) throws IOException {
    indexDataService.exportCsv(request, response);
  }

}
