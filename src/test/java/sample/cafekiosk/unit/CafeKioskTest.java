package sample.cafekiosk.unit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverage.Americano;
import sample.cafekiosk.unit.beverage.Latte;
import sample.cafekiosk.unit.order.Order;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


class CafeKioskTest {

    //@DisplayName("1. 음료 1개 추가 테스트")
    @DisplayName("1. 음료 1개를 추가하면 주문 목록에 담긴다.")
    @Test
    void add01() {
        CafeKiosk cafeKiosk = new CafeKiosk();

        cafeKiosk.add(new Americano());

        assertThat(cafeKiosk.getBerverages()).hasSize(1);
        assertThat(cafeKiosk.getBerverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @DisplayName("키오스크에 음료를 여러 잔 추가할 수 있다.")
    @Test
    void add_several_berverages() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano, 2);

        assertThat(cafeKiosk.getBerverages().get(0)).isEqualTo(americano);
        assertThat(cafeKiosk.getBerverages().get(1)).isEqualTo(americano);

        assertThat(cafeKiosk.getBerverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @DisplayName("키오스크에 0 이하의 음료를 추가하면 IAE를 던진다.")
    @Test
    void add_zero_berverages() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        assertThatThrownBy(() -> cafeKiosk.add(americano, -3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("음료는 1잔 이상 주문하실 수 있습니다.");
    }

    @DisplayName("키오스크에 추가된 음료를 지울 수 있다.")
    @Test
    void remove() {
        CafeKiosk kiosk = new CafeKiosk();
        Americano americano = new Americano();

        kiosk.add(americano);
        assertThat(kiosk.getBerverages()).hasSize(1);

        kiosk.remove(americano);
        assertThat(kiosk.getBerverages()).isEmpty();
    }

    @DisplayName("키오스크에 추가된 모든 음료를 지울 수 있다.")
    @Test
    void clear() {
        CafeKiosk kiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        kiosk.add(americano);
        kiosk.add(latte);

        assertThat(kiosk.getBerverages()).hasSize(2);

        kiosk.clear();
        assertThat(kiosk.getBerverages()).isEmpty();
    }

    @DisplayName("주문 목록에 담긴 상품들의 총 금액을 계산할 수 있다.")
    @Test
    void calculateTotalPrice() {
        // given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        // when
        cafeKiosk.add(americano);
        cafeKiosk.add(latte);

        // then
        int total = cafeKiosk.calculateTotalPrice();
        assertThat(total).isEqualTo(8500);
    }


    @Disabled(value = "worse case")
    @Test
    void create_order() {
        CafeKiosk kiosk = new CafeKiosk();
        Americano americano = new Americano();

        kiosk.add(americano);
        Order order = kiosk.createOrder();

        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    //@DisplayName("특정 시간 이전에 주문을 생성하면 실패한다.")
    @DisplayName("영업 시작 시간 이전에는 주문을 생성할 수 없다..")
    @Test
    void create_order_with_current_time() {
        CafeKiosk kiosk = new CafeKiosk();
        Americano americano = new Americano();

        kiosk.add(americano);
        Order order = kiosk.createOrder(LocalDateTime.of(2023, 10, 30, 14, 0));

        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @DisplayName("현재 시간을 파라미터로 받아서 주문한다. [예외]")
    @Test
    void create_order_with_not_open() {
        CafeKiosk kiosk = new CafeKiosk();
        Americano americano = new Americano();

        kiosk.add(americano);

        assertThatThrownBy(() -> kiosk.createOrder(LocalDateTime.of(2023, 10, 30, 7, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문시간이 아닙니다. 관리자에게 문의하세요.");
    }
}
