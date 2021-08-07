package hello.core.member;

/**
 * 회원 서비스 구현체
 *
 * 생성자를 통해서 어떤 구현 객체를 주입할지는 오직 외부 AppConfig 에서 결정되므로,
 * 생성자를 통해 어떤 구현 객체가 주입될지는 알 수 없다.
 *
 * DIP 완성 : MemberServiceImpl 은 MemberRepository 인 추상에만 의존하면 되고, 구체 클래스를 몰라도 된다.
 * 관심사의 분리 : 객체를 생성하고 연결하는 역할과 실행하는 역할이 명확히 분리되었다.
 */
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    
    // 생성자
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
}
