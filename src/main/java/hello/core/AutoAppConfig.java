package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * @ComponentScan 어노테이션
 * : @Component 애노테이션이 붙은 클래스를 스캔해서 스프링 빈으로 등록한다.
 *
 * 컴포넌트 스캔을 사용하려면 먼저 @ComponentScan 을 설정 정보에 붙여주면 된다.
 *
 * 컴포넌트 스캔을 사용하면 @Configuration 이 붙은 설정 정보도 자동으로 등록되기 때문에
 * AppConfig, TestConfig 등 앞서 만들어두었던 설정 정보도 함께 등록되고, 실행되어 버린다.
 *
 * => excludeFilters 를 이용해서 설정정보는 컴포넌트 스캔 대상에서 제외
 *
 * @Component 어노테이션
 * : 스프링 빈의 기본 이름은 클래스명을 사용하되 맨 앞글자만 소문자를 사용
 * : 스프링 빈의 이름을 직접 지정하고 싶으면 @Component("이름") 형식으로 이름을 부여
 *   ( 예: @Component("memberService2") )
 *
 * @Autowired 어노테이션
 * : 생성자에 @Autowired 를 지정하면 스프링 컨테이너가 자동으로 해당 스프링 빈을 찾아서 주입
 * : 기본 조회 전략 : 타입이 같은 빈을 찾아서 주입
 * : getBean(MemberRepository.class) 와 같은 개념
 * : 생성자에 파라미터가 많아도 다 찾아서 자동으로 주입
 */
@Configuration
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {


}
