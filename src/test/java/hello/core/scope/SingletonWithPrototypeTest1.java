package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 프로토타입 스코프 - 싱글톤 빈과 함께 사용시 문제점
 *  : 스프링 컨테이너에 프토토타입 스코프의 빈을 요청하면 항상 새로운 객체 인스턴스를 생성해서 반환한다.
 *  : 스프링은 일반적으로 싱글톤 빈을 사용하므로, 싱글톤 빈이 프로토타입 빈을 사용하게 된다.
 *  : 그런데 싱글톤 빈은 생성 시점에만 의존관계 주입을 받기 때문에, 프로토타입 빈이 새로 생성되기는 하지만,
 *    싱글톤 빈과 함께 계속 유지되는 것이 문제다.
 *
 * 참고)
 * 여러 빈에서 같은 프로토타입 빈을 주입 받으면, 주입 받는 시점에 각각 새로운 프로토타입 빈이 생성 된다.
 */
public class SingletonWithPrototypeTest1 {

    /**
     * 프로토타입 빈 직접 요청
     *
     * 스프링 컨테이너에 프로토타입 빈 직접 요청1
     *  1. 클라이언트A 는 스프링 컨테이너에 프로토타입 빈을 요청한다.
     *  2. 스프링 컨테이너는 프로토타입 빈을 새로 생성해서 반환(x01) 한다. 해당 빈의 count 필드 값은 0이다.
     *  3. 클라이언트는 조회한 프로토타입 빈에 addCount() 를 호출하면서 count 필드를 +1 한다.
     *    => 결과적으로 프로토타입 빈(x01) 의 count 는 1이 된다.
     *
     * 스프링 컨테이너에 프로토타입 빈 직접 요청2
     *  1. 클라이언트B 는 스프링 컨테이너에 프로토타입 빈을 요청한다.
     *  2. 스프링 컨테이너는 프로토타입 빈을 새로 생성해서 반환(x02) 한다. 해당 빈의 count 필드 값은 0이다.
     *  3. 클라이언트는 조회한 프로토타입 빈에 addCount() 를 호출하면서 count 필드를 +1 한다.
     *    => 결과적으로 프로토타입 빈(x02) 의 count 는 1이 된다.
     */
    @Test
    void prototypeFind() {

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();

        Assertions.assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();

        Assertions.assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    /**
     * 싱글톤 빈에서 프로토타입 빈 사용
     *
     * 싱글톤에서 프로토타입 빈 사용1
     *  : clientBean 은 싱글톤이므로, 보통 스프링 컨테이너 생성 시점에 함께 생성되고, 의존관계 주입도 발생한다.
     *  1. clientBean 은 의존관계 자동 주입을 사용한다. 주입 시점에 스프링 컨테이너에 프로토타입 빈을 요청한다.
     *  2. 스프링 컨테이너는 프로토타입 빈을 생성해서 clientBean 에 반환한다. 프로토타입 빈의 count 필드 값은 0이다.
     *  => clientBean 은 프로토타입 빈을 내부 필드에 보관, 정확히는 참조값을 보관한다.
     *
     * 싱글톤에서 프로토타입 빈 사용2
     *  : 클라이언트A 는 clientBean 을 스프링 컨테이너에 요청해서 받는다. 싱글톤이므로 항상 같은 clientBean 이 반환된다.
     *  3. 클라이언트A 는 clientBean.logic() 을 호출한다.
     *  4. clientBean 은 prototypeBean 의 addCount() 를 호출해서 프로토타입 빈의 count를 증가한다.
     *    => count 값이 1이 된다.
     *
     * 싱글톤에서 프로토타입 빈 사용3
     *  : 클라이언트B 는 clientBean 을 스프링 컨테이너에 요청해서 받는다. 싱글톤이므로 항상 같은 clientBean 이 반환된다.
     *  : clientBean 이 내부에 가지고 있는 프로토타입 빈은 이미 과거에 주입이 끝난 빈이다.
     *  : 주입 시점에 스프링 컨테이너에 요청해서 프로토타입 빈이 새로 생성이 된 것이지 사용 할 때마다 새로 생성되는 것이 아니다.
     *  5. 클라이언트B 는 clientBean.logic() 을 호출한다.
     *  6. clientBean 은 prototypeBean 의 addCount() 를 호출해서 프로토타입 빈의 count 를 증가한다.
     *    => 원래 count 값이 1 이었으므로 2 가 된다.
     */
    @Test
    void singletonClientUsePrototype() {

        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();

        Assertions.assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();

        Assertions.assertThat(count2).isEqualTo(2);
    }

    @Scope("singleton")
    static class ClientBean {

        private final PrototypeBean prototypeBean;

        @Autowired
        public ClientBean(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

        public int logic() {

            prototypeBean.addCount();

            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototypeBean {

        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init" + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
