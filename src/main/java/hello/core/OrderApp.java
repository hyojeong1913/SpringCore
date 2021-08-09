package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.order.Order;
import hello.core.order.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {

    public static void main(String[] args) {

        // AppConfig 를 통해서 관심사를 확실하게 분리
//        AppConfig appConfig = new AppConfig();

//        MemberService memberService = appConfig.memberService();
//        OrderService orderService = appConfig.orderService();

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);

        Long memberId = 1L;

        // 회원 가입
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        // 주문 생성
        Order order = orderService.createOrder(memberId, "itemA", 20000);

        // 생성된 주문 확인하여 할인 금액 조회
        System.out.println("order = " + order);
        System.out.println("order.calculatePrice() = " + order.calculatePrice());
    }
}
