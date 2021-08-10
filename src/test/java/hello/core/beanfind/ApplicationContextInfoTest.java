package hello.core.beanfind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {

    // 스프링 컨테이너 생성
    // ApplicationContext 에는 beanDefinition 정보를 조회할 필요가 없어 getBeanDefinition 같은 복잡한 메소드가 구현되어 있지 않다.
    // beanDefinition 정보를 조회하는 getBeanDefinition 등의 메소드들을 사용하기 위해 AnnotationConfigApplicationContext 사용
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    /**
     * 스프링에 등록된 모든 빈 정보를 출력
     */
    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBean() {

        // 스프링에 등록된 모든 빈 이름을 조회
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) {

            // 빈 이름으로 빈 객체(인스턴스)를 조회
            Object bean = ac.getBean(beanDefinitionName);

            System.out.println("name = " + beanDefinitionName + " object = " + bean);
        }
    }

    /**
     * getRole() 을 사용하여 스프링이 내부에서 등록한 빈만 출력 또는 직접 등록한 빈만 출력
     *
     * ROLE_APPLICATION : 일반적으로 사용자가 정의한 빈
     * ROLE_INFRASTRUCTURE : 스프링이 내부에서 사용하는 빈
     */
    @Test
    @DisplayName("애플리케이션 빈 출력하기")
    void findApplicationBean() {

        // 스프링에 등록된 모든 빈 이름을 조회
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) {

            // 각 빈에 대한 메타데이터 정보
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            // getRole() 을 통해 빈 조건 조회
            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {

                // 빈 이름으로 빈 객체(인스턴스)를 조회
                Object bean = ac.getBean(beanDefinitionName);

                System.out.println("name = " + beanDefinitionName + " object = " + bean);
            }
        }
    }
}
