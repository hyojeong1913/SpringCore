package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 상태를 유지할 경우 발생하는 문제점 예시
 *
 * ThreadA가 사용자 A 코드를 호출하고 ThreadB가 사용자 B 코드를 호출한다고 가정
 * StatefulService 의 price 필드는 공유되는 필드인데, 특정 클라이언트가 값을 변경
 */
class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {

        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        statefulService1.order("userA", 10000); // ThreadA : A 사용자 10000원 주문
        statefulService2.order("userB", 20000); // ThreadB : B 사용자 20000원 주문

        // ThreadA: 사용자 A 주문 금액 조회
        int price = statefulService1.getPrice();

        System.out.println("price = " + price);

        // 사용자 A의 주문금액은 10000원이 되어야 하는데, 20000원이라는 결과가 나온다.
        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }

    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}