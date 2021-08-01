package hello.core.member;

import java.util.HashMap;
import java.util.Map;

/**
 * 메모리 회원 저장소 구현체
 *
 * 데이터베이스가 아직 확정되지 않았다는 가정 하에 가장 단순한 메모리 회원 저장소를 구현하기로 결정
 *
 * 참고) HashMap 을 사용하여 동시성 이슈가 발생한 경우 ConcurrentHashMap 사용
 */
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
