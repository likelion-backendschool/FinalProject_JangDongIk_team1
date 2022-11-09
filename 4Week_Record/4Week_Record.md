## Title: [4Week] 장동익

### 미션 요구사항 분석 & 체크리스트

---

### 필수과제
- [x] JWT 로그인 구현(POST /api/v1/member/login)
- [x] 내 도서 리스트 구현(GET /api/v1/myBooks)
- [x] 내 도서 상세정보 구현(GET /api/v1/myBooks/{id})
- [ ] 로그인 한 회원의 정보 구현(GET /api/v1/member/me)
- [x] Spring Doc 으로 API 문서화(크롬 /swagger-ui/index.html )

### 추가과제
- [x] 엑세스 토큰 화이트리스트 구현(Member 엔티티에 accessToken 필드 추가)
- [ ] 리액트에서 작동되도록

### 4주차 미션 요약

---

**[접근 방법]**

- 개발 방법
    - 우선 강의 유튜브를 복습 후에 개발 진행
    - 내용 중 모르는 내용은 위키에 메모하며 복습
    - 최대한 같은 기능 단위로 Commit을 하지만, 한 커밋 내역이 너무 길어지지 않도록
    - dev branch에 개발 진행 후 마지막에 PR 생성
    - ApiV1Controller를 따로 생성해서 API와 관련된 코드 분리
    - MVC 구조를 지키기
    - 전에 진행했던 프로젝트와 강의 시간에 배운 내용을 토대로 구현

- 개발 순서
    - SpringDoc와 관련된 세팅
    - Postman과 SpringDoc를 이용해 Body에 Json 데이터를 넣어가며 API 기능 작동 확인
    - Test Case를 작성하며 기능 작동 확인
    - JWT -> login API -> me API -> myBooks API 순으로 구현

1. SpringDoc
    - Swagger와 관련된 코드를 가장 최우선으로 구현
    - Test Case와 함께 API의 기능 작동을 계속해서 확인
    - 오류 발생 시 Http Code를 확인해 문제 지점 확인

2. JWT, API
    - 기존 MemberController와 분리해서 ApiV1Controller를 생성해 로그인 폼 처리
    - 로그인에 성공할 경우 AccessToken 생성 후 Member의 accessToken 필드에 저장
    - 로그인 유효성 검사를 통해 잘못된 로그인 시도 처리
    - RsData를 도입해 응답에는 항상 응답코드, 응답메시지, Data가 나오도록 처리

**[특이사항]**

- 수정 필요 사항
    - 회원의 상세 정보에서 CreateDate와 ModifyDate가 제대로 반환되지 않는 문제
    - 리액트 코드 내에서 로그인 시 잘못된 로그인 정보는 에러 메시지를 출력하나 올바른 로그인 정보를 입력 시 로그인이 되지 않는 문제