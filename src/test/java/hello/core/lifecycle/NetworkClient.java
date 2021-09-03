package hello.core.lifecycle;

/**
 * 데이터베이스 커넥션 풀이나 네트워크 소켓처럼 애플리케이션 시작 시점에 필요한 연결을 미리 해두고,
 * 애플리케이션 종료 시점에 연결을 모두 종료하는 작업을 진행하려면, 객체의 초기화와 종료 작업이 필요하다.
 *
 * InitializingBean 은 afterPropertiesSet() 메서드로 초기화 지원
 * DisposableBean 은 destroy() 메서드로 소멸 지원
 *
 * 초기화, 소멸 인터페이스 단점
 * - 스프링 전용 인터페이스로 해당 코드가 스프링 전용 인터페이스에 의존한다.
 * - 초기화, 소멸 메서드의 이름을 변경할 수 없다.
 * - 직접 코드를 고칠 수 없는 외부 라이브러리에 적용할 수 없다.
 *
 * 설정 정보를 사용하도록 변경
 * : 설정 정보에 @Bean(initMethod = "init", destroyMethod = "close") 처럼
 *   초기화, 소멸 메서드를 지정할 수 있다.
 *
 * 설정 정보 사용 특징
 * - 메서드 이름을 자유롭게 줄 수 있다.
 * - 스프링 빈이 스프링 코드에 의존하지 않는다.
 * - 코드가 아니라 설정 정보를 사용하기 때문에 코드를 고칠 수 없는 외부 라이브러리에도 초기화, 종료 메서드를 적용할 수 있다.
 */
public class NetworkClient {

    private String url;

    public NetworkClient() {

        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 서비스 시작 시 호출
     */
    public void connect() {
        System.out.println("connect : " + url);
    }

    public void call(String message) {
        System.out.println("call : " + url + " message = " + message);
    }

    /**
     * 서비스 종료 시 호출
     */
    public void disconnect() {
        System.out.println("close : " + url);
    }

    /**
     * 초기화 메서드가 의존관계 주입 완료 후에 적절하게 호출된 것을 확인
     *
     * @throws Exception
     */
    public void init() {

        System.out.println("NetworkClient.init");

        connect();

        call("초기화 연결 메시지");
    }

    /**
     * 스프링 컨테이너의 종료가 호출되자 소멸 메서드가 호출된 것도 확인
     *
     * @throws Exception
     */
    public void close() {

        System.out.println("NetworkClient.close");

        disconnect();
    }
}
