package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class AllBeanTest {

    @Test
    void findAllBean() {

        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);

        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "userA", Grade.VIP);

        // fixDiscountPolicy 사용
        int discountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");

        // fixDiscountPolicy 검증
        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(discountPrice).isEqualTo(1000);

        // rateDiscountPolicy 사용
        int rateDiscountPrice = discountService.discount(member, 20000, "rateDiscountPolicy");

        // rateDiscountPolicy 검증
        assertThat(rateDiscountPrice).isEqualTo(2000);
    }

    /**
     * Map 으로 모든 DiscountPolicy 를 주입받는다.
     *  => fixDiscountPolicy , rateDiscountPolicy 가 주입된다.
     *
     * 만약 해당하는 타입의 스프링 빈이 없으면, 빈 컬렉션이나 Map 을 주입한다.
     */
    static class DiscountService {

        // map 의 키에 스프링 빈의 이름을 넣어주고, 그 값으로 DiscountPolicy 타입으로 조회한 모든 스프링 빈을 담아준다.
        private final Map<String, DiscountPolicy> policyMap;

        // DiscountPolicy 타입으로 조회한 모든 스프링 빈을 담아준다.
        private final List<DiscountPolicy> policies;

        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {

            this.policyMap = policyMap;
            this.policies = policies;

            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
        }

        /**
         * discountCode 로
         *  => "fixDiscountPolicy" 가 넘어오면 map 에서 fixDiscountPolicy 스프링 빈을 찾아서 실행
         *  => "rateDiscountPolicy" 가 넘어오면 map 에서 rateDiscountPolicy 스프링 빈을 찾아서 실행
         *
         * @param member
         * @param price
         * @param discountCode
         * @return
         */
        public int discount(Member member, int price, String discountCode) {

            DiscountPolicy discountPolicy = policyMap.get(discountCode);

            return discountPolicy.discount(member, price);
        }
    }
}
