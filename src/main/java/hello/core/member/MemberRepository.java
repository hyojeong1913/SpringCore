package hello.core.member;

/**
 * 회원 저장소 인터페이스
 */
public interface MemberRepository {

    /**
     * 회원 가입
     *
     * @param member
     */
    void save(Member member);

    /**
     * 회원 조회
     *
     * @param memberId
     * @return
     */
    Member findById(Long memberId);
}
