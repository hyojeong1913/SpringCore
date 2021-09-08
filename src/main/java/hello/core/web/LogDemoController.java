package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Logger 가 잘 작동하는지 확인하는 테스트용 컨트롤러
 */
@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
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
    public String logDemo(HttpServletRequest request) {

        // 받은 requestURL 값을 myLogger 에 저장
        String requestURL = request.getRequestURI().toString();

        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");

        logDemoService.logic("testId");

        return "OK";
    }
}
