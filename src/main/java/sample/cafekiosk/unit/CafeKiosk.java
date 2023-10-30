package sample.cafekiosk.unit;

import lombok.Getter;
import sample.cafekiosk.unit.beverage.Beverage;
import sample.cafekiosk.unit.order.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CafeKiosk {

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

    public Order createOrder() {
        return new Order(LocalDateTime.now(), berverages);
    }

    public int caculateTotalPrice() {
        int totalPrice = 0;
        for (Beverage berverage : berverages) {
            totalPrice += berverage.getPrice();
        }
        return totalPrice;
    }
}