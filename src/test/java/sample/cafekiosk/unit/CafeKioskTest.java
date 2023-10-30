package sample.cafekiosk.unit;

import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverage.Americano;
import sample.cafekiosk.unit.beverage.Latte;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


class CafeKioskTest {

    @Test
    void add_manual_test() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        System.out.println(">>> 담긴 음료 수: " + cafeKiosk.getBerverages().size());
        System.out.println(">>> 담긴 음료: " + cafeKiosk.getBerverages().get(0).getName());
    }


    @Test
    void add() {
        CafeKiosk cafeKiosk = new CafeKiosk();

        cafeKiosk.add(new Americano());

        assertThat(cafeKiosk.getBerverages()).hasSize(1);
        assertThat(cafeKiosk.getBerverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @Test
    void remove() {
        CafeKiosk kiosk = new CafeKiosk();
        Americano americano = new Americano();

        kiosk.add(americano);
        assertThat(kiosk.getBerverages()).hasSize(1);

        kiosk.remove(americano);
        assertThat(kiosk.getBerverages()).isEmpty();
    }

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

}