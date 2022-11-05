## Title: [3Week] 장동익

### 미션 요구사항 분석 & 체크리스트

---

### 필수과제
- [x] 관리자회원
- [x] 관리자페이지
- [x] 정산데이터 생성
- [x] 건별정산처리
- [x] 전체정산처리

### 추가과제
- [ ] 정산데이터를 배치로 생성
- [ ] 출금신청기능(사용자)
- [ ] 출금처리기능(관리자)

### 3주차 미션 요약

---

**[접근 방법]**

- 개발 방법
    - dev branch에 개발 후 마지막에 PR 생성
    - MVC 구조를 지키기
    - 전에 진행했던 프로젝트와 강의 시간에 배운 내용을 토대로 구현

- 개발 순서
    - 관리자 회원 생성 후 관리자 페이지 구현
    - 정산 데이터 생성 후 개별&전체 정산 처리 구현

1. 관리자 페이지
    - authLevel이 3이면 일반회원, 7이면 관리자 회원으로
    - admin 계정을 생성 후에 관리자 권한 부여
    - id : admin, pw : admin

2. 정산
    - 월별로 결제가 완료된 상품을 가져와 정산 리스트로 생성
    - 정산 리스트 내에서 건별 정산 처리를 진행
    - 정산된 금액은 예치금으로 넣어줌 (비율은 판매자:관리자 = 5:5)
    - 전체 정산 처리는 건별 정산 처리를 체크 박스와 ids를 이용해 구현

**[특이사항]**

- 수정 필요 사항
    - 배치 구현을 위해 공부를 하다가 제출 기간이 임박해서 구현을 못함
    - 정산 완료 알림이 정상적으로 표시되지 않는 문제
    - Test case 추가
    - 코드 가독성을 위한 세세한 주석 추가