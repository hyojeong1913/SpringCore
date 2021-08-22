package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * memberRepository 인스턴스는 모두 같은 인스턴스가 공유되어 사용된다.
 *
 * 스프링 컨테이너가 각각 @Bean을 호출해서 스프링 빈을 생성
 */
public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {

        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        // 모두 같은 인스턴스를 참고하고 있다.
        System.out.println("memberService -> memberRepository1 = " + memberRepository1);
        System.out.println("orderService -> memberRepository2 = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);

        // 모두 같은 인스턴스를 참고하고 있다.
        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
    }

    /**
     * 스프링 컨테이너는 싱글톤 레지스트리이므로, 스프링 빈이 싱글톤이 되도록 보장해주어야 한다.
     * 스프링은 클래스의 바이트코드를 조작하는 라이브러리를 사용한다.
     *
     * 내가 만든 클래스가 아닌 스프링이 CGLIB 라는 바이트코드 조작 라이브러리를 사용해서 AppConfig 클래스를 상속받은 임의의 다른 클래스를 만들고,
     * 그 다른 클래스를 스프링 빈으로 등록한 것이다.
     *
     * 그 임의의 다른 클래스가 바로 싱글톤이 보장되도록 해준다.
     *
     * @Bean 이 붙은 메서드마다 이미 스프링 빈이 존재하면 존재하는 빈을 반환하고,
     * 스프링 빈이 없으면 생성 해서 스프링 빈으로 등록하고 반환하는 코드가 동적으로 만들어진다.
     */
    @Test
    void configurationDeep() {

        //  AnnotationConfigApplicationContext 에 파라미터로 넘긴 값은 스프링 빈으로 등록되므로 AppConfig 도 스프링 빈이 된다.
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        AppConfig bean = ac.getBean(AppConfig.class);

        // hello.core.AppConfig$$EnhancerBySpringCGLIB$$6c35e3a6
        // 클래스 명에 xxxCGLIB 가 붙어 있다.
        System.out.println("bean.getClass() = " + bean.getClass());
    }
}
