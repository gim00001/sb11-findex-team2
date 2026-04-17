package com.sprint.mission.findex.domain.indexdata.repository.querydsl;

import com.sprint.mission.findex.domain.indexdata.dto.IndexDataListRequest;
import com.sprint.mission.findex.domain.indexdata.entity.IndexData;
import com.sprint.mission.findex.global.common.dto.CursorPageResponse;

public interface IndexDataCustomRepository {
  CursorPageResponse<IndexData> findAll(IndexDataListRequest request);
}