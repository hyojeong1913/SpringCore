package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class OrderApp {

    public static void main(String[] args) {

        MemberService memberService = new MemberServiceImpl();
        OrderService orderService = new OrderServiceImpl();

        Long memberId = 1L;

        // 회원 가입
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        // 주문 생성
        Order order = orderService.createOrder(memberId, "itemA", 10000);

        // 생성된 주문 확인하여 할인 금액 조회
        System.out.println("order = " + order);
        System.out.println("order.calculatePrice() = " + order.calculatePrice());
    }
}
