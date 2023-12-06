package sample.cafekiosk.spring.api.controller.order.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.api.service.order.request.OrderCreateServiceRequest;

import javax.validation.constraints.Size;
import java.util.List;


@Getter
@NoArgsConstructor
public class OrderCreateRequest {

    @Size(min = 1, message = "주문에는 하나 이상의 상품이 포함되어야 합니다.")
    private List<String> productNumbers;

    @Builder
    private OrderCreateRequest(List<String> productNumbers) {
        this.productNumbers = productNumbers;
    }

    public OrderCreateServiceRequest toServiceRequest() {
        return OrderCreateServiceRequest.builder().productNumbers(productNumbers).build();
    }
}
