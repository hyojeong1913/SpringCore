package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 주문 서비스 구현체
 *
 * 생성자를 통해서 어떤 구현 객체를 주입할지는 오직 외부 AppConfig 에서 결정되므로,
 * 생성자를 통해 어떤 구현 객체가 주입될지는 알 수 없다.
 *
 * OrderServiceImpl 은 DiscountPolicy 인터페이스만 의존
 *
 * @Component 어노테이션 : 스프링 빈으로 등록
 *
 * @RequiredArgsConstructor 어노테이션
 * : lombok 라이브러리가 제공하는 기능으로 final 이 붙은 필드들을 모아서 생성자를 자동으로 만들어준다.
 * = lombok 이 java 의 어노테이션 프로세서 라는 기능을 이용해서 컴파일 시점에 생성자 코드를 자동으로 생성해준다.
 */
@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    /**
     * 생성자
     *
     * @Autowired 어노테이션 : 의존관계를 자동으로 주입
     * => 생성자에서 여러 의존관계도 한 번에 주입받을 수 있다.
     *
     * @param memberRepository
     * @param discountPolicy
     */
//    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        // 회원 정보 조회
        Member member = memberRepository.findById(memberId);

        // 할인 정책에 따른 각 회원의 할인 가격 조회
        int discountPrice = discountPolicy.discount(member, itemPrice);

        // 주문 객체를 생성해서 반환
        return new Order(memberId, itemName, itemPrice, discountPrice);
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
