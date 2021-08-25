package hello.core.scan.filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.context.annotation.ComponentScan.*;

/**
 * 설정 정보와 전체 테스트 코드
 *
 * includeFilters 에 MyIncludeComponent 애노테이션을 추가해서 BeanA 가 스프링 빈에 등록된다.
 * excludeFilters 에 MyExcludeComponent 애노테이션을 추가해서 BeanB 는 스프링 빈에 등록되지 않는다.
 *
 * FilterType 옵션
 * - ANNOTATION : 기본값, 애노테이션을 인식해서 동작
 * - ASSIGNABLE_TYPE : 지정한 타입과 자식 타입을 인식해서 동작
 * - ASPECTJ : AspectJ 패턴 사용
 * - REGEX : 정규 표현식
 * - CUSTOM : TypeFilter 이라는 인터페이스를 구현해서 처리
 */
public class ComponentFilterAppConfigTest {

    @Test
    void filterScan() {

        ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);

        BeanA beanA = ac.getBean("beanA", BeanA.class);
        Assertions.assertThat(beanA).isNotNull();

        assertThrows(
                NoSuchBeanDefinitionException.class,
                () -> ac.getBean("beanB", BeanB.class)
        );
    }

    /**
     * includeFilters
     * : 컴포넌트 스캔 대상을 추가로 지정
     *
     * excludeFilters
     * : 컴포넌트 스캔에서 제외할 대상을 지정
     */
    @Configuration
    @ComponentScan(
            includeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),
            excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class)
    )
    static class ComponentFilterAppConfig {

    }

    /**
     * 참고) 스프링 빈에서 BeanA 도 빼고 싶은 경우
     */
//    @ComponentScan(
//            includeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),
//            excludeFilters = {
//                    @Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class),
//                    @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = BeanA.class)
//            }
//    )
}
