package hello.core.discount;

import hello.core.member.Member;

/**
 * 할인 정책 인터페이스
 */
public interface DiscountPolicy {

    /**
     * 할인되는 금액 조회
     *
     * @param member
     * @param price
     * @return 할인 대상 금액
     */
    int discount(Member member, int price);
}
