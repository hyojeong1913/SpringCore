package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 스프링 빈 라이프사이클
 * 1. 스프링 빈은 객체를 생성하고, 의존관계 주입이 다 끝난 다음에야 필요한 데이터를 사용할 수 있는 준비가 완료된다.
 * 2. 초기화 작업은 의존관계 주입이 모두 완료되고 난 다음에 호출해야 한다.
 *
 * 스프링은 의존관계 주입이 완료되면 스프링 빈에게 콜백 메서드를 통해서 초기화 시점을 알려주는 다양한 기능을 제공한다.
 * 또한, 스프링은 스프링 컨테이너가 종료되기 직전에 소멸 콜백을 준다.
 * => 안전하게 종료 작업을 진행 가능
 *
 * 스프링 빈의 이벤트 라이프사이클
 * : 스프링 컨테이너 생성 -> 스프링 빈 생성 -> 의존관계 주입 -> 초기화 콜백 -> 사용 -> 소멸 전 콜백 -> 스프링 종료
 *
 * 초기화 콜백
 * : 빈이 생성되고, 빈의 의존관계 주입이 완료된 후 호출
 *
 * 소멸 전 콜백
 * : 빈이 소멸되기 직전에 호출
 *
 * 참고)
 * - 생성자는 필수 정보(파라미터)를 받고, 메모리를 할당해서 객체를 생성하는 책임을 가지는 반면에
 *   초기화는 이렇게 생성된 값들을 활용해서 외부 커넥션을 연결하는 등 무거운 동작을 수행한다.
 *   => 객체를 생성하는 부분과 초기화 하는 부분을 명확하게 나누는 것이 유지보수 관점에서 좋다.
 *
 * - 싱글톤 빈들은 스프링 컨테이너가 종료될 때 싱글톤 빈들도 함께 종료되기 때문에
 *   스프링 컨테이너가 종료되기 직전에 소멸 전 콜백이 일어난다.
 * - 싱글톤 처럼 컨테이너의 시작과 종료까지 생존하는 빈도 있지만,
 *   생명주기가 짧은 빈들도 있는데 이 빈들은 컨테이너와 무관하게 해당 빈이 종료되기 직전에 소멸 전 콜백이 일어난다.
 *
 * 스프링이 지원하는 빈 생명주기 콜백
 * - 인터페이스 (InitializingBean, DisposableBean)
 * - 설정 정보에 초기화 메서드, 종료 메서드 지정
 * - @PostConstruct, @PreDestory 애노테이션 지원
 */
public class BeanLifeCycleTest {

    /**
     * 객체를 생성하는 단계에는 url 이 없고,
     * 객체를 생성한 다음에 외부에서 수정자 주입을 통해서 setUrl() 이 호출되어야 url 이 존재하게 된다.
     */
    @Test
    public void lifeCycleTest() {

        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);

        NetworkClient client = ac.getBean(NetworkClient.class);

        ac.close();
    }

    /**
     * 라이프사이클 환경 설정
     */
    @Configuration
    static class LifeCycleConfig {

        /**
         * 설정 정보에 초기화 소멸 메서드 지정
         *
         * 종료 메서드 추론
         * : @Bean 의 destroyMethod 는 기본값이 (inferred) (추론)으로 등록되어 있다.
         * : close , shutdown 등 이름 그대로 종료 메서드를 추론해서 자동으로 호출해준다.
         *   => 직접 스프링 빈으로 등록하면 종료 메서드는 따로 적어주지 않아도 잘 동작한다.
         * : 추론 기능을 사용하기 싫으면 destroyMethod="" 처럼 빈 공백을 지정하면 된다.
         *
         * @return
         */
        @Bean
        public NetworkClient networkClient() {

            NetworkClient networkClient = new NetworkClient();

            networkClient.setUrl("http://hello-spring.dev");

            return networkClient;
        }
    }
}
