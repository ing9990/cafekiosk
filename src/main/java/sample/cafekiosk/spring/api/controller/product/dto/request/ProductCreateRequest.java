package sample.cafekiosk.spring.api.controller.product.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import javax.validation.constraints.*;

@Getter
@NoArgsConstructor
public class ProductCreateRequest {

    @NotNull(message = "상품 타입은 필수입니다.")
    private ProductType type;
    @NotNull(message = "상품 판매상태는 필수입니다.")
    private ProductSellingStatus sellingStatus;

    // @Max를 사용해서 글자 수에 대한 Validation을 할 수 있지만 과연 Presentation Layer에서 질 책임인지는 다시 생각해봐야됨.
    // @Max(20)
    @NotBlank(message = "상품 이름은 필수입니다.")
    // @NotNull // "", "  " 가능
    // @NotEmpty // "   " 가능
    private String name;

    @Positive(message = "상품 가격은 양수여야합니다.")
    private int price;

    @Builder
    private ProductCreateRequest(ProductType type, String name, int price, ProductSellingStatus sellingStatus) {
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

    public ProductCreateServiceRequest toServiceRequest() {
        return ProductCreateServiceRequest.builder()
                .type(type)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }
}
