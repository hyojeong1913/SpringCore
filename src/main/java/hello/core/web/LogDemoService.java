package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

/**
 * 비즈니스 로직이 있는 서비스 계층
 *
 * ObjectProvider.getObject() 를 LogDemoController, LogDemoService 에서 각각 한번씩 따로 호출해도
 * 같은 HTTP 요청이면 같은 스프링 빈이 반환된다.
 */
@Service
@RequiredArgsConstructor
public class LogDemoService {

    private final ObjectProvider<MyLogger> myLoggerProvider;

    /**
     * 서비스 계층은 웹 기술에 종속되지 않고, 가급적 순수하게 유지하는 것이 유지보수 관점에서 좋다.
     *
     * request scope 의 MyLogger 덕분에 이런 부분을 파라미터로 넘기지 않고,
     * MyLogger 의 멤버변수에 저장해서 코드와 계층을 깔끔하게 유지 가능
     *
     * @param id
     */
    public void logic(String id) {

        MyLogger myLogger = myLoggerProvider.getObject();

        myLogger.log("service id = " + id);
    }
}
