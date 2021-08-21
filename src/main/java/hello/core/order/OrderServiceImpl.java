package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;

/**
 * 주문 서비스 구현체
 *
 * 생성자를 통해서 어떤 구현 객체를 주입할지는 오직 외부 AppConfig 에서 결정되므로,
 * 생성자를 통해 어떤 구현 객체가 주입될지는 알 수 없다.
 *
 * OrderServiceImpl 은 DiscountPolicy 인터페이스만 의존
 */
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    // 생성자
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

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
