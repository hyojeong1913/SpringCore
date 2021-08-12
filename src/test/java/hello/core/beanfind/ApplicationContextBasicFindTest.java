package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 스프링 컨테이너에서 스프링 빈을 찾는 가장 기본적인 조회 방법
 *
 * ac.getBean(빈이름, 타입)
 * ac.getBean(타입)
 *
 * 역할과 구현을 구분해야하고 역할에 의존해야하는 것이 좋기 때문에 인터페이스 타입으로 조회가 바람직
 */
class ApplicationContextBasicFindTest {

    // 스프링 컨테이너 생성
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName() {
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("이름 없이 타입으로만 조회")
    void findBeanByType() {
        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    /**
     * 구체 타입으로 조회하면 변경시 유연성이 떨어진다.
     */
    @Test
    @DisplayName("구체 타입으로 조회")
    void findBeanByName2() {
        MemberService memberService = ac.getBean("memberService", MemberServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    /**
     * 조회 대상 스프링 빈이 없으면 예외 발생하는데, 해당 예외가 터지는지 확인
     * JUnit assertions.asserThrow 사용
     */
    @Test
    @DisplayName("빈 이름으로 조회X")
    void findBeanByNameX() {
        // 오른쪽 파라미터의 로직이 실행하면 왼쪽 예외가 터지면 성공
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("xxxxx", MemberServiceImpl.class));
    }
}
