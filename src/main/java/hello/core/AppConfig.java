package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 구현 객체를 생성하고, 연결하는 책임을 가지는 별도의 설정 클래스
 *
 * DI(Dependency Injection) = 의존관계 주입 또는 의존성 주입
 * 
 * 애플리케이션의 실제 동작에 필요한 구현 객체를 생성
 * 생성한 객체 인스턴스의 참조(레퍼런스)를 생성자를 통해서 주입(연결)
 *
 * => AppConfig 의 등장으로 애플리케이션이 크게 사용 영역과, 객체를 생성하고 구성 (Configuration) 하는 영역으로 분리되었다.
 *
 * @Configuration : AppConfig에 설정을 구성한다는 뜻의 어노테이션
 * @Bean : 각 메서드에 붙여주면 스프링 컨테이너에 스프링 빈으로 등록된다.
 */
@Configuration
public class AppConfig {

    /**
     * memoryMemberRepository 객체를 생성 후, 그 참조값을 memberServiceImpl 을 생성하면서 생성자로 전달
     *
     * @return
     */
    @Bean
    public MemberService memberService() {

        // memberService() 호출 로그
        System.out.println("call AppConfig.memberService");

        return new MemberServiceImpl(memberRepository());
    }

    /**
     * memoryMemberRepository 객체 및 fixDiscountPolicy 객체를 생성 후, 그 참조값을 orderServiceImpl 을 생성하면서 생성자로 전달
     *
     * @return
     */
    @Bean
    public OrderService orderService() {

        // orderService() 호출 로그
        System.out.println("call AppConfig.orderService");

        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    /**
     * 회원 저장소 역할
     *
     * new MemoryMemberRepository() 부분 중복 제거
     * 장점 : MemoryMemberRepository 를 다른 구현체로 변경할 때 한 부분만 변경하면 된다.
     * @return
     */
    @Bean
    public MemberRepository memberRepository() {

        // memberRepository() 호출 로그
        System.out.println("call AppConfig.memberRepository");

        return new MemoryMemberRepository();
    }

    /**
     * 할인 정책 역할
     *
     * new FixDiscountPolicy() 부분 중복 제거
     * 장점 : FixDiscountPolicy 를 다른 구현체로 변경할 때 한 부분만 변경하면 된다.
     * 
     * @return
     */
    @Bean
    public DiscountPolicy discountPolicy() {
        // 정액 할인 정책을 정률% 할인 정책으로 변경
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
