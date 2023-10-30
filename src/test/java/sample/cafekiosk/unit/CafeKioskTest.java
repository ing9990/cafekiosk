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

    @Test
    void add_manual_test() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        System.out.println(">>> 담긴 음료 수: " + cafeKiosk.getBerverages().size());
        System.out.println(">>> 담긴 음료: " + cafeKiosk.getBerverages().get(0).getName());
    }


    @DisplayName("키오스크에 음료를 추가할 수 있다.")
    @Test
    void add() {
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
        assertThat(kiosk.getBerverages()).hasSize(0);
    }


    // 이 테스트는 현재 시간에 따라 결과가 바뀐다.
    @DisplayName("주문하기 [테스트에 따라 결과가 바뀜.]")
    @Disabled
    @Test
    void create_order() {
        CafeKiosk kiosk = new CafeKiosk();
        Americano americano = new Americano();

        kiosk.add(americano);
        Order order = kiosk.createOrder();

        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @DisplayName("현재 시간을 받아서 주문한다. [성공]")
    @Test
    void create_order_with_current_time() {
        CafeKiosk kiosk = new CafeKiosk();
        Americano americano = new Americano();

        kiosk.add(americano);
        Order order = kiosk.createOrder(LocalDateTime.of(2023, 10, 30, 14, 0));

        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @DisplayName("현재 시간을 받아서 주문한다. [예외]")
    @Test
    void create_order_with_not_open() {
        CafeKiosk kiosk = new CafeKiosk();
        Americano americano = new Americano();

        kiosk.add(americano);

        assertThatThrownBy(() -> kiosk.createOrder(LocalDateTime.of(2023, 10, 30, 07, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문시간이 아닙니다. 관리자에게 문의하세요.");
    }
}











