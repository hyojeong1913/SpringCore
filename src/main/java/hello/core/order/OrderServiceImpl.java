package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 주문 서비스 구현체
 *
 * 생성자를 통해서 어떤 구현 객체를 주입할지는 오직 외부 AppConfig 에서 결정되므로,
 * 생성자를 통해 어떤 구현 객체가 주입될지는 알 수 없다.
 *
 * OrderServiceImpl 은 DiscountPolicy 인터페이스만 의존
 *
 * @Component 어노테이션 : 스프링 빈으로 등록
 *
 * @RequiredArgsConstructor 어노테이션
 * : lombok 라이브러리가 제공하는 기능으로 final 이 붙은 필드들을 모아서 생성자를 자동으로 만들어준다.
 * = lombok 이 java 의 어노테이션 프로세서 라는 기능을 이용해서 컴파일 시점에 생성자 코드를 자동으로 생성해준다.
 */
@Component
//@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    /**
     * 생성자
     *
     * @Autowired 어노테이션 : 의존관계를 자동으로 주입
     * => 생성자에서 여러 의존관계도 한 번에 주입받을 수 있다.
     *
     * @Autowired 는 타입으로 조회하기 때문에 선택된 빈이 2개 이상일 때 문제가 발생 ( NoUniqueBeanDefinitionException 오류가 발생 )
     *
     * 조회 대상 빈이 2개 이상일 때 해결 방법
     * - @Autowired 필드 명 매칭
     *   : 타입 매칭을 시도하고, 이때 여러 빈이 있으면 필드 이름, 파라미터 이름으로 빈 이름을 추가 매칭
     *   ( 필드 명 매칭은 먼저 타입 매칭을 시도하고 그 결과에 여러 빈이 있을 때 추가로 동작 )
     * - @Qualifier 사용
     *   : 주입 시에 @Qualifier 를 붙여주고 등록한 이름을 붙여준다.
     *   : 단점) 모든 코드에 @Qualifier 를 붙여주어야 한다.
     * - @Primary 사용
     *   : 우선순위를 정하는 방법
     *   : @Autowired 시에 여러 빈이 매칭되면 @Primary 가 우선권을 가진다.
     *
     * @Qualifier vs @Primary
     *  : 메인 데이터 베이스의 커넥션을 획득하는 스프링 빈은 @Primary 를 적용해서 편리하게 조회
     *  : 서브 데이터베이스 커넥션 빈을 획득할 때는 @Qualifier 를 지정해서 명시적으로 획득
     *  : @Primary 는 기본값 처럼 동작하는 것이고, @Qualifier 는 매우 상세하게 동작
     *  : 스프링은 자동보다는 수동이, 넒은 범위의 선택권 보다는 좁은 범위의 선택권이 우선 순위가 높으므로 @Qualifier 가 우선권이 높다.
     *
     * 직접 만든 어노테이션 @MainDiscountPolicy 사용
     *
     * @param memberRepository
     * @param discountPolicy
     */
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        // 회원 정보 조회
        Member member = memberRepository.findById(memberId);

        // 할인 정책에 따른 각 회원의 할인 가격 조회
        int discountPrice = discountPolicy.discount(member, itemPrice);

        // 주문 객체를 생성해서 반환
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    /**
     * 스프링 컨테이너가 싱글톤의 문제점을 해결하는지 검증 테스트 용도
     *
     * @return
     */
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
