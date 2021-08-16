package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * AppConfig 는 요청을 할 때 마다 객체를 새로 생성
 *
 * 단점 : 메모리 낭비가 심하다. (예: 고객 트래픽이 초당 100이 나오면 초당 100개 객체가 생성되고 소멸된다.)
 * => 해결방안 : 싱글톤 패턴 (해당 객체가 딱 1개만 생성되고, 공유하도록 설계하면 된다.)
 */
public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {

        AppConfig appConfig = new AppConfig();

        // 1. 조회 : 호출할 때마다 객체 생성
        MemberService memberService1 = appConfig.memberService();
        MemberService memberService2 = appConfig.memberService();

        // 2. 참조값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // memberService1 != memberService2
        Assertions.assertThat(memberService1).isNotSameAs(memberService2);
    }
}
