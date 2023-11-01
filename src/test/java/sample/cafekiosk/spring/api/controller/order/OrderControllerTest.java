package sample.cafekiosk.spring.api.controller.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.spring.api.service.order.OrderService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @MockBean
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @DisplayName("신규 주문을 등록한다.")
    @Test
    void createOrder() throws Exception {
        // given
        List<String> productNumbers = List.of("001", "002");
        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumbers(productNumbers)
                .build();

        // when // then
        mockMvc.perform(
                        post("/api/v1/orders/new")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(om.writeValueAsString(request))
                ).
                andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
        ;

    }

    @DisplayName("신규 주문을 등록할 때 한 개 이상의 상품번호가 있어야 한다.")
    @Test
    void createOrderWithEmptyList() throws Exception {
        // given
        List<String> productNumbers = Collections.emptyList();
        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumbers(productNumbers)
                .build();

        // when // then
        mockMvc.perform(
                        post("/api/v1/orders/new")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(om.writeValueAsString(request))
                ).
                andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("주문에는 하나 이상의 상품이 포함되어야 합니다."))
        ;

    }
}