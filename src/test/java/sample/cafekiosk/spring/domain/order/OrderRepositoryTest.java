package sample.cafekiosk.spring.domain.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.groups.Tuple.tuple;
import static sample.cafekiosk.spring.domain.order.OrderStatus.INIT;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.*;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

@SpringBootTest
@Transactional
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("특정 두 시간 사이에 특정 주문상태에 해당하는 주문을 가져올 수 있다.")
    @Test
    void findOrdersBy() {
        // given
        Product product1 = createProduct("001", HANDMADE, "아메리카노", HOLD, 6000);
        Product product2 = createProduct("002", HANDMADE, "팥빙수", STOP_SELLING, 5000);
        Product product3 = createProduct("003", HANDMADE, "카페라떼", SELLING, 4000);
        productRepository.saveAll(List.of(product1, product2, product3));

        orderRepository.save(Order.create(LocalDateTime.of(2023, 11, 1, 0, 0, 0), List.of(product1, product2)));
        orderRepository.save(Order.create(LocalDateTime.of(2023, 12, 1, 0, 0, 0), List.of(product2, product3)));
        orderRepository.save(Order.create(LocalDateTime.of(2024, 3, 3, 0, 0, 0), List.of(product2)));

        LocalDateTime startDateTime = LocalDateTime.of(2023, 10, 31, 0, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2024, 10, 31, 0, 0, 0);

        // when
        List<Order> result = orderRepository.findOrdersBy(startDateTime, endDateTime, INIT);

        // then
        Assertions.assertThat(result)
                .hasSize(3)
                .extracting("registredDateTime", "totalPrice", "orderStatus")
                .containsExactlyInAnyOrder(
                        tuple(LocalDateTime.of(2023, 11, 1, 0, 0, 0), 11000, INIT),
                        tuple(LocalDateTime.of(2023, 12, 1, 0, 0, 0), 9000, INIT),
                        tuple(LocalDateTime.of(2024, 3, 3, 0, 0, 0), 5000, INIT)
                );
    }

    private Product createProduct(String productNumber, ProductType type, String productName, ProductSellingStatus productSellingStatus, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .type(type)
                .sellingStatus(productSellingStatus)
                .name(productName)
                .price(price)
                .build();
    }

}