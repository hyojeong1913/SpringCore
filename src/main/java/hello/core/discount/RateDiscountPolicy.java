package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;

public class RateDiscountPolicy implements DiscountPolicy {

    // 10% 할인
    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {

        // VIP 인 경우 10% 할인 금액 리턴
        if (member.getGrade() == Grade.VIP) {
            return price * discountPercent / 100;
        } else {
            return 0;
        }
    }
}
