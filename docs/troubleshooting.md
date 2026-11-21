# 트러블슈팅 기록

## 1. 404, 405, 400 에러가 전부 500으로 나옴

`@ExceptionHandler(Exception.class)`로 전역 처리를 걸었더니 Spring이 원래 처리하던 예외까지 다 잡아서 500으로 내려줬다.
`NoResourceFoundException`, `HttpRequestMethodNotSupportedException`, `HttpMessageNotReadableException` 처리기를 따로 추가해서 해결.
`Exception` 처리기는 마지막에 걸리는 용도로만 써야 한다.

## 2. 메뉴 등록 시 `Connection is read-only`

서비스 클래스에 `@Transactional(readOnly = true)`를 걸어 두고, 등록 메서드에 `@Transactional`을 빼먹었다.
읽기 전용 커넥션으로 INSERT를 하려다 실패한 것. 쓰기 메서드에 `@Transactional` 추가해서 해결.

## 3. 테이블명 오타 수정 후 FK 에러

`sotres`로 오타 난 테이블명을 `stores`로 고쳤는데, `menu_items`의 FK가 계속 `sotres`를 참조하고 있었다.
`ddl-auto: update`는 기존 FK를 수정하지 않는다. 테이블을 drop하고 다시 생성해서 해결.

## 4. LazyInitializationException (직접 재현)

트랜잭션 밖에서 LAZY로 가져온 `store`에 접근해 봤다.
`getId()`는 프록시가 이미 갖고 있어서 괜찮았지만, `getName()`은 DB 조회가 필요해서 `no Session` 에러가 났다.
엔티티는 트랜잭션 안에서 DTO로 바꿔서 반환하는 걸로 정리. `open-in-view`는 끈 상태 유지.

## 5. 가게 목록 N+1

가게 3개 조회에 쿼리가 4번 나갔다. 가게마다 `owner.getName()`을 호출하면서 사장님을 하나씩 조회했기 때문.
처음엔 사장님이 1명뿐이라 1차 캐시 때문에 안 보였고, 사장님을 3명으로 늘리니까 드러났다.

세 가지 방법을 비교

| 방법 | 쿼리 수 |
|---|---|
| `join fetch` | 1 |
| `@EntityGraph` | 1 |
| `default_batch_fetch_size` | 2 (`in`으로 묶어서 조회) |

batch size는 전역 설정으로 켜 두고, 가게 목록은 `join fetch`로 1번에 가져오도록 했다.