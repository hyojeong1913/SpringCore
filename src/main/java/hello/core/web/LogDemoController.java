package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Logger 가 잘 작동하는지 확인하는 테스트용 컨트롤러
 *
 * 스프링 애플리케이션을 실행하는 시점에 싱글톤 빈은 생성해서 주입이 가능하지만, request 스코프 빈은 아직 생성되지 않는다.
 * 실제 request 가 와야 생성 가능하므로 애플리케이션 실행 시점에 오류 발생
 * => Provider 사용
 *
 * ObjectProvider.getObject() 를 LogDemoController, LogDemoService 에서 각각 한번씩 따로 호출해도
 * 같은 HTTP 요청이면 같은 스프링 빈이 반환된다.
 *
 * CGLIB 라는 라이브러리로 내 클래스를 상속 받은 가짜 프록시 객체를 만들어서 주입한다.
 */
@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;

    // ObjectProvider 덕분에 ObjectProvider.getObject() 를 호출하는 시점까지 request scope 빈의 생성을 지연
    private final MyLogger myLogger;

    /**
     * HttpServletRequest 를 통해서 요청 URL 을 받는다.
     * requestURL 값 : http://localhost:8080/log-demo
     *
     * myLogger 는 HTTP 요청 당 각각 구분되므로 다른 HTTP 요청 때문에 값이 섞이지 않는다.
     *
     * 참고)
     * requestURL 을 MyLogger 에 저장하는 부분은 컨트롤러 보다는
     * 공통 처리가 가능한 스프링 인터셉 터나 서블릿 필터 같은 곳을 활용하는 것이 좋다.
     *
     * @param request
     * @return
     */
    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) throws InterruptedException {

        // 받은 requestURL 값을 myLogger 에 저장
        String requestURL = request.getRequestURI().toString();

        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");

        // request 시작지점과 종료 지점 격차가 멀어지더라도 같은 request 빈이 호출된다는 것을 확인해보기 위해 임시로 sleep 추가
        Thread.sleep(1000);

        logDemoService.logic("testId");

        return "OK";
    }
}
