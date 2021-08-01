package hello.core.member;

/**
 * 회원 저장소 인터페이스
 */
public interface MemberRepository {

    // 회원가입
    void save(Member member);

    // 회원 조회
    Member findById(Long memberId);
}
