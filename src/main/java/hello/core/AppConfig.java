package hello.core;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

/**
 * 구현 객체를 생성하고, 연결하는 책임을 가지는 별도의 설정 클래스
 *
 * DI(Dependency Injection) = 의존관계 주입 또는 의존성 주입
 * 
 * 애플리케이션의 실제 동작에 필요한 구현 객체를 생성
 * 생성한 객체 인스턴스의 참조(레퍼런스)를 생성자를 통해서 주입(연결)
 */
public class AppConfig {

    /**
     * memoryMemberRepository 객체를 생성 후, 그 참조값을 memberServiceImpl 을 생성하면서 생성자로 전달
     *
     * @return
     */
    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    /**
     * memoryMemberRepository 객체 및 fixDiscountPolicy 객체를 생성 후, 그 참조값을 orderServiceImpl 을 생성하면서 생성자로 전달
     *
     * @return
     */
    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }
}
