package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * @ComponentScan 어노테이션
 * : @Component 애노테이션이 붙은 클래스를 스캔해서 스프링 빈으로 등록한다.
 *
 * 컴포넌트 스캔을 사용하려면 먼저 @ComponentScan 을 설정 정보에 붙여주면 된다.
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
 *
 * @ComponentScan 여러 옵션
 * - excludeFilters
 *  : 컴포넌트 스캔 대상에서 제외
 *    => 이 예제에서는 컴포넌트 스캔을 사용하면 @Configuration 이 붙은 설정 정보도 자동으로 등록되기 때문에
 *       AppConfig, TestConfig 등 앞서 만들어두었던 설정 정보도 함께 등록되고, 실행되는 걸 방지하기 위해 설정정보는 컴포넌트 스캔 대상에서 제외
 * - basePackages
 *  : 탐색할 패키지의 시작 위치를 지정
 *  : 이 패키지를 포함해서 하위 패키지를 모두 탐색
 *  : 여러 시작 위치를 지정할 수도 있다. ( 예: basePackages = {"hello.core", "hello.service"} )
 *  : 만약 지정하지 않으면 @ComponentScan 이 붙은 설정 정보 클래스의 패키지가 시작 위치가 된다.
 *    => 이 예제에서는 이 클래스의 패키지인 hello.core 패키지부터 탐색
 *  : 권장 방법
 *    => 패키지 위치를 지정하지 않고, 설정 정보 클래스의 위치를 프로젝트 최상 단에 두는 것
 *    => 메인 설정 정보는 프로젝트를 대표하는 정보이기 때문에 더 좋을 수 있다.
 *    => 예) com.hello, com.hello.service, com.hello.repository 등의 클래스가 있는 경우,
 *          프로젝트 시작 루트인 com.hello 에 AppConfig 같은 메인 설정 정보를 두고,
 *          @ComponentScan 애노테이션을 붙이고, basePackages 지정은 생략한다.
 *          => com.hello 를 포함한 하위는 모두 자동으로 컴포넌트 스캔의 대상이 된다.
 *
 * 컴포넌트 스캔 기본 대상
 * - 컴포넌트 스캔은 @Component 뿐만 아니라 다음과 내용도 추가로 대상에 포함
 * - @Component 어노테이션
 *  : 컴포넌트 스캔에서 사용
 * - @Controlller 어노테이션
 *  : 스프링 MVC 컨트롤러에서 사용
 *  : 스프링 MVC 컨트롤러로 인식
 * - @Service 어노테이션
 *  : 스프링 비즈니스 로직에서 사용
 *  : 특별한 처리는 없으나, 비즈니스 계층을 인식하는데 도움이 된다.
 * - @Repository 어노테이션
 *  : 스프링 데이터 접근 계층에서 사용
 *  : 스프링 데이터 접근 계층으로 인식하고, 데이터 계층의 예외를 스프링 예외로 변환
 * - @Configuration 어노테이션
 *  : 스프링 설정 정보에서 사용
 *  : 스프링 설정 정보로 인식하고, 스프링 빈이 싱글톤을 유지하도록 추가 처리
 *
 * 참고) useDefaultFilters 옵션은 기본으로 켜져있는데, 이 옵션을 끄면 기본 스캔 대상들이 제외된다.
 */
@Configuration
@ComponentScan(
        basePackages = "hello.core",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

    /**
     * 수동 빈 등록 vs 자동 빈 등록
     * : 수동 빈 등록이 우선권을 가지므로 수동 빈이 자동 빈을 오버라이딩 해버린다.
     *
     * 수동 빈 등록 시
     * Overriding bean definition for bean 'memoryMemberRepository' with a different definition: replacing
     * 와 같은 에러 로그가 남는다.
     *
     * 최근 스프링 부트에서는 수동 빈 등록과 자동 빈 등록이 충돌나면 오류가 발생하도록 기본 값을 바꾸었다.
     * - 수동 빈 등록, 자동 빈 등록 오류시 스프링 부트 에러
     *      Consider renaming one of the beans or enabling overriding by setting
     *      spring.main.allow-bean-definition-overriding=true
     * - 만약 충돌 시 오류가 나지 않도록 설정하고 싶은 경우 application.properties 에 코드 추가
     *      spring.main.allow-bean-definition-overriding=true
     *
     * @return
     */
//    @Bean(name = "memoryMemberRepository")
//    MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }
}
