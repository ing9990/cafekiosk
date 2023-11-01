package sample.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.*;
import static sample.cafekiosk.spring.domain.product.ProductType.*;

// @SpringBootTest
@ActiveProfiles("test")
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("원하는 판매상태를 가진 상품들을 조회한다.")
    @Test
    void findAllBySellingStatusIn() {
        // given
        Product product1 = createProduct("001", HANDMADE, "아메리카노", SELLING);
        Product product2 = createProduct("002", HANDMADE, "카페라떼", HOLD);
        Product product3 = createProduct("003", HANDMADE, "팥빙수", STOP_SELLING);
        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<Product> products = productRepository.findAllBySellingStatusIn(List.of(SELLING, HOLD));

        // then
        assertThat(products).hasSize(2);
        assertThat(products).extracting("productNumber", "name", "sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", SELLING),
                        tuple("002", "카페라떼", HOLD)
                );
    }

    @DisplayName("상품번호 리스트로 상품들을 조회한다.")
    @Test
    void findAllByProductNumberIn() {
        // given
        Product product1 = createProduct("001", HANDMADE, "아메리카노", SELLING);
        Product product2 = createProduct("002", HANDMADE, "카페라떼", HOLD);
        Product product3 = createProduct("003", HANDMADE, "팥빙수", STOP_SELLING);

        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<Product> products = productRepository.findAllByProductNumberIn(List.of("001", "002"));

        // then
        assertThat(products).hasSize(2);
        assertThat(products).extracting("productNumber", "name", "sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", SELLING),
                        tuple("002", "카페라떼", HOLD)
                );
    }


    @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽어온다.")
    @Test
    void findLatestProductNumber() {
        // given
        Product product1 = createProduct("001", HANDMADE, "아메리카노", SELLING);
        Product product2 = createProduct("002", HANDMADE, "카페라떼", HOLD);
        String targetProductNumber = "003";
        Product product3 = createProduct(targetProductNumber, HANDMADE, "팥빙수", STOP_SELLING);

        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        String productNumber = productRepository.findLatestProductNumber();

        assertThat(productNumber).isEqualTo("003");
    }

    @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽어올 때, 상품이 하나도 없는 경우에는 null을 반환한다.")
    @Test
    void findLatestProductNumberWhenProductIsEmpty() {
        // when
        String productNumber = productRepository.findLatestProductNumber();

        assertThat(productNumber).isNull();
    }

    private Product createProduct(String productNumber, ProductType type, String productName, ProductSellingStatus productSellingStatus) {
        return Product.builder()
                .productNumber(productNumber)
                .type(type)
                .sellingStatus(productSellingStatus)
                .name(productName)
                .price(4000)
                .build();
    }
}


















