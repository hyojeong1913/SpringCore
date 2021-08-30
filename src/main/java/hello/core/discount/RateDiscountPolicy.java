package hello.core.discount;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @Component 어노테이션 : 스프링 빈으로 등록
 * @MainDiscountPolicy 어노테이션 : hello.core.annotation 디렉토리에 직접 만든 어노테이션
 */
@Component
@MainDiscountPolicy
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
