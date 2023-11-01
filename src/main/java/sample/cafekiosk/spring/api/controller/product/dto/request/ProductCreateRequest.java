package sample.cafekiosk.spring.api.controller.product.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

@Getter
@NoArgsConstructor
public class ProductCreateRequest {
    private String productNumber;
    private ProductSellingStatus sellingStatus;
    private ProductType type;
    private String name;
    private int price;

    @Builder
    private ProductCreateRequest(String productNumber, ProductType type, String name, int price, ProductSellingStatus sellingStatus) {
        this.productNumber = productNumber;
        this.sellingStatus = sellingStatus;
        this.type = type;
        this.name = name;
        this.price = price;
    }

    public Product toEntity(String nextProductNumber) {
        return Product.builder()
                .productNumber(nextProductNumber)
                .type(this.type)
                .sellingStatus(this.sellingStatus)
                .name(this.name)
                .price(this.price)
                .build();
    }
}
