package by.kuropatin.dkr.transformer;

import by.kuropatin.dkr.model.Order;
import by.kuropatin.dkr.model.response.OrderResponse;
import org.springframework.stereotype.Component;

@Component
public final class OrderTransformer {

    public OrderResponse transformToOrderResponse(final Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .status(order.getStatus())
                .orderDate(order.getOrderDate())
                .items(order.getItems())
                .build();
    }
}