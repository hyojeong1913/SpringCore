package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 회원 서비스 구현체
 *
 * 생성자를 통해서 어떤 구현 객체를 주입할지는 오직 외부 AppConfig 에서 결정되므로,
 * 생성자를 통해 어떤 구현 객체가 주입될지는 알 수 없다.
 *
 * DIP 완성 : MemberServiceImpl 은 MemberRepository 인 추상에만 의존하면 되고, 구체 클래스를 몰라도 된다.
 * 관심사의 분리 : 객체를 생성하고 연결하는 역할과 실행하는 역할이 명확히 분리되었다.
 *
 * @Component 어노테이션 : 스프링 빈으로 등록
 */
@Component
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    /**
     * 생성자
     *
     * 이전에 AppConfig 에서는 @Bean 으로 직접 설정 정보를 작성했고, 의존관계도 직접 명시했으나
     * 설정 정보 자체가 없기 때문에, 의존관계 주입도 이 클래스 안에서 해결해야 한다.
     *
     * @Autowired 어노테이션 : 의존관계를 자동으로 주입
     * 
     * @param memberRepository
     */
    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    /**
     * 스프링 컨테이너가 싱글톤의 문제점을 해결하는지 검증 테스트 용도
     *
     * @return
     */
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
