package hello.core.order;

/**
 * 주문 서비스 인터페이스
 */
public interface OrderService {

    /**
     * 주문
     * 
     * @param memberId
     * @param itemName
     * @param itemPrice
     * @return
     */
    Order createOrder(Long memberId, String itemName, int itemPrice);
}
