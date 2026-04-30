# Findex

금융위원회 Open API 기반 한국 주가지수 분석 백엔드 서비스

> 공공데이터 Open API 연동, 이동평균·등락률 금융 지표 계산, Spring Scheduler 자동 수집 파이프라인, Railway.io 실서비스 배포까지 포함한 백엔드 프로젝트

<details>
  <summary>Table of Contents</summary>

- [Contributors](#contributors--code-slayers)
- [Built With](#built-with)
- [Getting Started](#getting-started)
- [Features](#features)
- [Technical Challenges](#technical-challenges)
- [Wiki](#wiki)

</details>

## Contributors — [Code Slayers](https://github.com/sb11-code-slayers)

|                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;김호현 🚩                  |                                 김지혜                                 |                                  김관희                                  |                                     정재현                                     |                                    안준영                                    |                                김시현                                |
| :----------------------------------------------------------------------: | :--------------------------------------------------------------------: | :----------------------------------------------------------------------: | :----------------------------------------------------------------------------: | :--------------------------------------------------------------------------: | :------------------------------------------------------------------: |
| <img src="https://github.com/pintordev.png" width="100" alt="pintordev"> | <img src="https://github.com/gim00001.png" width="100" alt="gim00001"> | <img src="https://github.com/rhksgml54.png" width="100" alt="rhksgml54"> | <img src="https://github.com/jeongjae5310.png" width="100" alt="jeongjae5310"> | <img src="https://github.com/Junyeong-An.png" width="100" alt="Junyeong-An"> | <img src="https://github.com/shyunii.png" width="100" alt="shyunii"> |
|                  [pintordev](https://github.com/pintordev)                  |                 [gim00001](https://github.com/gim00001)                  |                  [rhksgml54](https://github.com/rhksgml54)                  |                   [jeongjae5310](https://github.com/jeongjae5310)                    |                   [Junyeong-An](https://github.com/Junyeong-An)                   |                 [shyunii](https://github.com/shyunii)                 |

## Built With

### Backend

![Spring Boot](https://img.shields.io/badge/Spring%20Boot%203.5.13-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=flat-square&logoColor=white)
![Spring Validation](https://img.shields.io/badge/Spring%20Validation-6DB33F?style=flat-square&logo=spring&logoColor=white)
![QueryDSL](https://img.shields.io/badge/QueryDSL%205.1.0-0769AD?style=flat-square&logo=hibernate&logoColor=white)
![MapStruct](https://img.shields.io/badge/MapStruct%201.5.5-6DB33F?style=flat-square&logo=spring&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok%201.18-BC4521?style=flat-square&logoColor=white)
![Swagger](https://img.shields.io/badge/SpringDoc%20OpenAPI%202.8.16-85EA2D?style=flat-square&logo=swagger&logoColor=black)

### Database & Infra

![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=flat-square&logo=postgresql&logoColor=white)
![H2](https://img.shields.io/badge/H2%20(dev%2Ftest)-09476B?style=flat-square&logo=h2database&logoColor=white)
![HikariCP](https://img.shields.io/badge/HikariCP-FF6F00?style=flat-square&logoColor=white)
![Caffeine](https://img.shields.io/badge/Caffeine%20Cache-6DB33F?style=flat-square&logo=spring&logoColor=white)
![Actuator](https://img.shields.io/badge/Spring%20Actuator-6DB33F?style=flat-square&logo=spring&logoColor=white)
![Railway](https://img.shields.io/badge/Railway-0B0D0E?style=flat-square&logo=railway&logoColor=white)

### Collaboration

![Git](https://img.shields.io/badge/Git-F05032?style=flat-square&logo=Git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=github&logoColor=white)
![Discord](https://img.shields.io/badge/Discord-5865F2?style=flat-square&logo=discord&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-000000?style=flat-square&logo=notion&logoColor=white)

## Getting Started

> 상세 설정(application.yml 프로필 구성, Railway 환경변수 등)은 [Wiki > Architecture](https://github.com/sb11-code-slayers/sb11-findex-team2/wiki/Architecture) 참고

```bash
# 로컬 실행 (H2 인메모리 DB, profile: local)
./gradlew bootRun

# 빌드
./gradlew build

# 테스트
./gradlew test
```

- Swagger UI: `http://localhost:8080/api/swagger-ui.html`
- H2 Console: `http://localhost:8080/h2-console`
- Frontend: `http://localhost:8080/`

## Features

| 모듈 | 엔드포인트 | 기능 |
| --- | --- | --- |
| 지수 정보 | `GET/POST/PATCH/DELETE /api/index-infos` | CRUD (Open API 자동 등록·수정 포함), 관련 데이터 cascade 삭제, 분류명·지수명 부분 일치 필터 + 즐겨찾기 필터 / 커서 페이지네이션, 요약 목록 |
| 지수 데이터 | `GET/POST/PATCH/DELETE /api/index-data`<br>`GET /api/index-data/export` | OHLCV 시계열 CRUD, 지수·날짜 범위 필터 / 커서 페이지네이션, CSV Export (동일 필터 적용, 페이지네이션 없음, UTF-8 BOM) |
| 연동 작업 | `GET/POST /api/sync-jobs` | 지수 정보 연동 (Open API 활용, 지수 데이터 동시 저장), 지수 데이터 연동 (지수×날짜 범위 지정), 연동 이력 조회 (유형·지수·날짜·작업자·결과 필터), 작업자 IP 마스킹 |
| 자동 연동 | `GET/PATCH /api/auto-sync-configs` | 지수 등록 시 비활성화 상태로 자동 생성, 활성화 ON/OFF, Spring Scheduler 매일 새벽 1시 증분 배치 |
| 대시보드 | `GET /api/index-data/performance`<br>`GET /api/index-data/{id}/chart` | 즐겨찾기 지수 성과 요약 (종가 기준) + 전일·전주·전월 대비 등락률 랭킹<br>종가 · 이동평균(MA5·MA20) 차트 (월/분기/년 시계열) |

## Technical Challenges

**커서 기반 페이지네이션**

오프셋 기반 페이지네이션은 데이터가 많아질수록 성능이 저하된다. `cursor` + `idAfter`(UUID) 조합으로 정렬 필드 값과 ID를 함께 활용하는 커서 페이지네이션을 구현했다. 정렬 필드 타입이 `String`과 `Integer`로 달라 QueryDSL의 `ComparableExpression`과 `NumberExpression`을 오버로딩으로 분리 처리했다.

**QueryDSL 도입**

동적 필터링(분류명, 지수명, 즐겨찾기 등)과 정렬 필드가 런타임에 결정되는 구조에서 JPQL 문자열 방식은 타입 안전성이 없다. QueryDSL의 `BooleanExpression` null 반환 패턴과 `Projections.constructor`를 활용해 컴파일 타임 검증과 필요한 컬럼만 조회하는 최적화를 동시에 달성했다.

**배치 연동 파이프라인**

Open API 호출 실패가 전체 배치를 롤백시키지 않도록 지수별 트랜잭션을 분리했다. 마지막 성공 연동 날짜를 `SyncJob` 이력에서 조회해 증분 동기화를 구현했다.

**이동평균 O(n) 슬라이딩 윈도우**

MA5·MA20 계산 시 매 포인트마다 윈도우 전체를 재합산하는 O(n×k) 방식에서, 이전 합에서 빠지는 값을 빼고 새 값을 더하는 슬라이딩 윈도우 O(n) 방식으로 개선했다.

**Spring AOP Self-invocation 트랜잭션 무력화**

동일 클래스 내에서 `@Transactional` 메서드를 직접 호출하면 Spring AOP 프록시가 우회되어 트랜잭션이 적용되지 않는다. 지수 데이터 연동 중 예외 발생 시 롤백되지 않고 부분 저장되는 문제로 발견됐다. 책임을 별도 Bean으로 분리해 해결했다.

**H2 vs PostgreSQL 동작 차이**

로컬(H2)에서 정상 동작하던 기능이 배포 환경(PostgreSQL)에서 다르게 동작하는 문제를 여러 차례 겪었다. `ORDER BY` 없이 삽입 순서를 보장하는 H2와 달리 PostgreSQL은 순서를 보장하지 않아 요약 목록 정렬이 불규칙하게 반환됐다. 각 케이스마다 PostgreSQL 기준으로 명시적 처리를 추가해 해결했다.

**Dashboard N+1 쿼리 제거**

즐겨찾기 성과 조회 API에서 지수별로 개별 쿼리가 발생해 200개 지수 기준 201개 쿼리가 실행됐다. `In-Clause` + `fetchJoin()` 배치 쿼리로 전면 리팩토링해 쿼리 수를 201개 → 1개(99.5% 감소)로 줄였고, 응답속도는 DAILY 기준 84ms → 8ms(90.5% 감소)로 개선됐다. DB에서 한 번에 가져온 뒤 Java Stream `groupingBy`로 메모리 내 그룹화·연산하는 방식을 채택했다. (#126)

**DB 인덱스 적용**

430만 건 `index_data` 시계열 정렬에 복합 인덱스를, 12만 건 `sync_job` 성공 이력 조회에 부분 인덱스(Partial Index)를, `index_info` 즐겨찾기 필터에 부분 인덱스를 추가했다. `sync_job` 마지막 성공 날짜 조회는 시퀀셜 스캔(Seq Scan)에서 인덱스 온리 스캔(Index Only Scan)으로 전환되어 20.59ms → 0.10ms(99.5% 감소)를 달성했다. (#142)

**커서 페이지네이션 count 쿼리 최적화**

모든 페이지 요청에서 `totalElements` count 쿼리를 실행하던 구조를 `cursor == null`인 첫 페이지에서만 실행하도록 변경했다. 이후 페이지는 count 쿼리를 생략하고 `totalElements: null`을 반환하며 클라이언트가 초기 값을 재사용한다. 5000페이지 기준 오프셋 대비 37.8% 응답속도 개선을 확인했다. (#111)

**KRX API 응답 Caffeine 캐시 적용**

휴장일 등 동일 날짜 범위를 반복 호출하는 경우 Open API를 재호출하지 않도록 `KrxOpenApiClientImpl.fetchByDateRange`에 Caffeine 캐시를 적용했다. 당일 데이터는 미확정이므로 `to >= today` 조건일 때는 캐싱을 제외한다. `maximumSize=500, expireAfterWrite=24h` 설정으로 운영 중이다. (#119)

**Dashboard API Caffeine 캐시 적용**

차트·성과·랭킹 API는 전체 `index_data`를 스캔하는 무거운 쿼리를 포함한다. `@Cacheable`로 서비스 레이어에 캐시를 적용해 반복 요청을 DB 조회 없이 처리한다. 데이터 변경(CRUD, 배치 연동 완료) 시 `@CacheEvict`로 즉시 무효화한다. `@Scheduled`와 `@CacheEvict`를 동시 사용할 수 없어 배치 완료 후 `CacheManager`로 프로그래매틱 evict를 처리했다. (#155)

## Wiki

상세 문서는 [GitHub Wiki](https://github.com/sb11-code-slayers/sb11-findex-team2/wiki)에서 확인할 수 있습니다.

| 문서                                          | 내용                                                           |
| --------------------------------------------- | -------------------------------------------------------------- |
| [Convention](https://github.com/sb11-code-slayers/sb11-findex-team2/wiki/Convention)           | 브랜치 전략, 커밋·이슈·PR 컨벤션, 코드 스타일                  |
| [Architecture](https://github.com/sb11-code-slayers/sb11-findex-team2/wiki/Architecture)       | 도메인 모듈 구조, ERD, 배치 프로세스 플로우, Railway 배포 환경 |
| [Tech Stack](https://github.com/sb11-code-slayers/sb11-findex-team2/wiki/Tech-Stack)           | 기술 선택 이유, 검토 후 제외한 기술, 핵심 설계 패턴            |
| [ADR](https://github.com/sb11-code-slayers/sb11-findex-team2/wiki/ADR)                         | 주요 아키텍처 결정 기록 (ADR-001 ~ ADR-013)                    |
| [API](https://github.com/sb11-code-slayers/sb11-findex-team2/wiki/API)                         | 엔드포인트 상세 스펙, 공통 응답 포맷, 에러 코드                |
| [Troubleshooting](https://github.com/sb11-code-slayers/sb11-findex-team2/wiki/Troubleshooting) | 공통 이슈, 팀원별 담당 모듈 트러블슈팅 (M1~M6)                 |
