package hello.core.xml;

import hello.core.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import static org.assertj.core.api.Assertions.*;

/**
 * xml 기반의 스프링 빈 설정 정보
 *
 * 장점 : 컴파일 없이 빈 설정 정보를 변경 가능
 */
public class XmlAppContext {

    @Test
    void xmlAppContext() {

        ApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");
        MemberService memberService = ac.getBean("memberService", MemberService.class);

        assertThat(memberService).isInstanceOf(MemberService.class);

    }
}
