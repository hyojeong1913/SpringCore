package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

/**
 * 주입할 스프링 빈이 없어도 동작해야 할 때가 있는데,
 * @Autowired 만 사용하면 required 옵션의 기본값이 true 로 되어 있어서 자동 주입 대상이 없으면 오류가 발생한다.
 *
 * 자동 주입 대상을 옵션으로 처리하는 방법
 * - @Autowired(required=false) : 자동 주입할 대상이 없으면 수정자 메서드 자체가 호출 안됨.
 * - org.springframework.lang.@Nullable : 자동 주입할 대상이 없으면 null 이 입력된다.
 * - Optional<> : 자동 주입할 대상이 없으면 Optional.empty 가 입력된다.
 */
public class AutowiredTest {

    @Test
    void autowiredOption() {

        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean {

        /**
         * Member 는 스프링 빈이 아니므로 자동 주입할 대상이 없어 수정자 메서드 자체가 호출 안됨
         *
         * @param noBean1
         */
        @Autowired(required = false)
        public void setNoBean1(Member noBean1) {
            System.out.println("noBean1 = " + noBean1);
        }

        /**
         * null 호출
         *
         * @param noBean2
         */
        @Autowired
        public void setNoBean2(@Nullable Member noBean2) {
            System.out.println("noBean1 = " + noBean2);
        }

        /**
         * Optional.empty 호출
         *
         * @param noBean3
         */
        @Autowired
        public void setNoBean3(Optional<Member> noBean3) {
            System.out.println("noBean1 = " + noBean3);
        }
    }
}
