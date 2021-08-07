package hello.core.order;

import hello.core.AppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {

    MemberService memberService;
    OrderService orderService;

    /**
     * @BeforeEach : 각 테스트를 실행하기 전에 호출됨.
     */
    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }

    @Test
    void createOrder() {

        Long memberId = 1L;

        // 회원 등급이 VIP 인 회원 가입
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        // 위에서 가입한 회원이 가격이 10000원인 상품 itemA 주문 생성
        Order order = orderService.createOrder(memberId, "itemA", 10000);

        // 위 주문에서 할인된 금액이 1000원이 맞는지 확인
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}
