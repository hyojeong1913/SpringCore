package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

/**
 * @Scope(value = "request") 를 사용해서 request 스코프로 지정
 *
 * 이 빈은 HTTP 요청 당 하나씩 생성되고, HTTP 요청이 끝나는 시점에 소멸된다.
 */
@Component
@Scope(value = "request")
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
