package by.kuropatin.dkr.service;

import by.kuropatin.dkr.exception.ApplicationException;
import by.kuropatin.dkr.model.Item;
import by.kuropatin.dkr.model.Order;
import by.kuropatin.dkr.model.OrderStatus;
import by.kuropatin.dkr.model.User;
import by.kuropatin.dkr.model.response.OrderResponse;
import by.kuropatin.dkr.repository.OrderRepository;
import by.kuropatin.dkr.transformer.OrderTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderTransformer transformer;
    private final UserService userService;

    public List<Order> getOrdersByUserId(final long userId) {
        return repository.findOrdersByUserId(userId);
    }

    public Order getOrderByIdAndUserId(final long orderId, final long userId) {
        return repository.findOrderByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new ApplicationException("Order with id %s was not found", orderId));
    }

    @Transactional(rollbackFor = Throwable.class)
    public OrderResponse placeOrder(final long userId) {
        final User user = userService.getUserById(userId);
        final Set<Item> items = new HashSet<>(user.getCart().getItems());

        final Order order = Order.builder()
                .user(user)
                .status(OrderStatus.NEW)
                .orderDate(LocalDate.now())
                .build();

        items.forEach(item -> {
            item.setCart(null);
            item.setOrder(order);
        });

        order.setItems(items);
        return transformer.transformToOrderResponse(repository.save(order));
    }
}