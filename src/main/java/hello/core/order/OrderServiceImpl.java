package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    // 메모리 회원 레포지토리를 구현체로 생성
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    // 할인 정책 변경 ( 고정 금액 할인 -> 정률 금액 할인 )
    // DiscountPolicy 인터페이스만 의존하는 것이 아닌 FixDiscountPolicy 또는 RateDiscountPolicy 구체 클래스도 함께 의존하고 있으므로
    //  => DIP 위반
    // FixDiscountPolicy 를 RateDiscountPolicy 로 변경하려는 순간
    //  => OCP 위반
    // 따라서, DIP 를 위반하지 않도록 인터페이스에만 의존하도록 의존관계 설계 변경 필요
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    // 인터페이스에만 의존하도록 코드 변경
    // 문제점 : 실제 구현체가 없어 이대로 실행 시 NPE (NULL Pointer Exception) 가 발생
    // 해결방법 : OrderServiceImpl 에 DiscountPolicy 의 구현 객체를 대신 생성하고 주입해주어야 한다.
    private DiscountPolicy discountPolicy;

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        // 회원 정보 조회
        Member member = memberRepository.findById(memberId);

        // 할인 정책에 따른 각 회원의 할인 가격 조회
        int discountPrice = discountPolicy.discount(member, itemPrice);

        // 주문 객체를 생성해서 반환
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
