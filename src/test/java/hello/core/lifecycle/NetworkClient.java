package hello.core.lifecycle;

/**
 * 데이터베이스 커넥션 풀이나 네트워크 소켓처럼 애플리케이션 시작 시점에 필요한 연결을 미리 해두고,
 * 애플리케이션 종료 시점에 연결을 모두 종료하는 작업을 진행하려면, 객체의 초기화와 종료 작업이 필요하다.
 */
public class NetworkClient {

    private String url;

    public NetworkClient() {

        System.out.println("생성자 호출, url = " + url);

        connect();

        call("초기화 연결 메시지");
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
}
