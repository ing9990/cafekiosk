package sample.cafekiosk.unit;

import lombok.Getter;
import sample.cafekiosk.unit.beverage.Beverage;
import sample.cafekiosk.unit.order.Order;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CafeKiosk {

    public static final LocalTime SHOP_OPEN_TIME = LocalTime.of(10, 0);
    public static final LocalTime SHOP_CLOSE_TIME = LocalTime.of(22, 0);

    private final List<Beverage> berverages = new ArrayList<>();

    public void add(Beverage berverage) {
        berverages.add(berverage);
    }

    public void add(Beverage berverage, int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("음료는 1잔 이상 주문하실 수 있습니다.");
        }

        for (int i = 0; i < count; i++) {
            berverages.add(berverage);
        }
    }


    public void remove(Beverage berverage) {
        berverages.remove(berverage);
    }

    public void clear() {
        berverages.clear();
    }

    @Deprecated
    public Order createOrder() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalTime currentTime = currentDateTime.toLocalTime();

        if (currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)) {
            throw new IllegalArgumentException("음료 주문 가능 시간이 아닙니다.");
        }

        return new Order(currentDateTime, berverages);
    }

    public Order createOrder(LocalDateTime currentDateTime) {
        LocalTime currentTime = currentDateTime.toLocalTime();

        if (currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)) {
            throw new IllegalArgumentException("주문시간이 아닙니다. 관리자에게 문의하세요.");
        }

        return new Order(currentDateTime, berverages);
    }

    public int caculateTotalPrice() {
        int totalPrice = 0;
        for (Beverage berverage : berverages) {
            totalPrice += berverage.getPrice();
        }
        return totalPrice;
    }
}
