package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

/**
 * @Scope(value = "request") 를 사용해서 request 스코프로 지정
 *
 * 이 빈은 HTTP 요청 당 하나씩 생성되고, HTTP 요청이 끝나는 시점에 소멸된다.
 *
 * proxyMode
 *  : CGLIB 라는 라이브러리로 내 클래스를 상속 받은 가짜 프록시 객체를 만들어서 주입한다.
 *  : 가짜 프록시 객체는 실제 요청이 오면 그때 내부에서 실제 빈을 요청하는 위임 로직이 들어있다.
 *  : 가짜 프록시 객체는 실제 request scope 와는 관계가 없으며, 내부에 단순한 위임 로직만 있고, 싱글톤처럼 동작
 *  : 프록시 객체 덕분에 클라이언트는 편리하게 request scope 를 사용 가능
 *  : 객체 조회를 꼭 필요한 시점까지 지연 처리한다.
 *  : 가짜 프록시 객체는 원본 클래스를 상속 받아서 만들어졌기 때문에 이 객체를 사용하는 클라이언트 입장에서는 동일하게 사용 가능 (다형성)
 *  : 적용 대상이 인터페이스가 아닌 클래스면 TARGET_CLASS 를 선택
 *    => @Scope 의 proxyMode = ScopedProxyMode.TARGET_CLASS) 를 설정하면
 *       스프링 컨테이너는 CGLIB 라는 바이트코드를 조작하는 라이브러리를 사용해서, MyLogger 를 상속받은 가짜 프록시 객체를 생성
 *  : 적용 대상이 인터페이스면 INTERFACES 를 선택
 */
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

    private String uuid;
    private String requestURL;

    /**
     * requestURL 정보도 추가로 넣어서 어떤 URL 을 요청해서 남은 로그인지 확인
     * requestURL 은 이 빈이 생성되는 시점에는 알 수 없으므로, 외부에서 setter 로 입력 받는다.
     *
     * @param requestURL
     */
    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    /**
     * 기대하는 공통 포맷 : [UUID][requestURL] {message}
     *
     * @param message
     */
    public void log(String message) {
        System.out.println("[" + uuid + "][" + requestURL + "] " + message);
    }

    /**
     * 이 빈이 생성되는 시점에 자동으로 @PostConstruct 초기화 메서드를 사용해서 uuid 를 생성해서 저장
     *
     * UUID 를 사용해서 HTTP 요청을 구분
     * 이 빈은 HTTP 요청 당 하나씩 생성되므로, uuid 를 저장해두면 다른 HTTP 요청과 구분 가능
     */
    @PostConstruct
    public void init() {

        uuid = UUID.randomUUID().toString();

        System.out.println("[" + uuid + "] request scope bean create : " + this);
    }

    /**
     * 이 빈이 소멸되는 시점에 @PreDestroy 를 사용해서 종료 메시지를 남긴다.
     */
    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close : " + this);
    }
}
