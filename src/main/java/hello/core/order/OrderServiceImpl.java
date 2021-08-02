package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    // 메모리 회원 레포지토리와 고정 금액 할인 정책 레포지토리를 구현체로 생성
    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

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
