package hello.core.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 회원 도메인 설계의 문제점
 * : 의존관계가 인터페이스 뿐만 아니라 구현까지 모두 의존하는 문제점 ( DIP 위반 )
 */
public class MemberServiceTest {

    MemberService memberService = new MemberServiceImpl();

    @Test
    void join() {

        // given
        Member member = new Member(1L, "memberA", Grade.VIP);

        // when
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        // then
        Assertions.assertThat(member).isEqualTo(findMember);
    }
}
