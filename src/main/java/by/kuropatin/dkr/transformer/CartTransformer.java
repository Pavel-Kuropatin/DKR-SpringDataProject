package by.kuropatin.dkr.transformer;

import by.kuropatin.dkr.model.Cart;
import by.kuropatin.dkr.model.response.CartResponse;
import by.kuropatin.dkr.model.response.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public final class CartTransformer {

    private final ItemTransformer itemTransformer;

    public CartResponse transformToCartResponse(final Cart cart) {
        final Set<ItemResponse> itemResponseSet = cart.getItems().stream()
                .map(itemTransformer::transformToItemResponse)
                .collect(Collectors.toSet());
        return CartResponse.builder()
                .id(cart.getId())
                .items(itemResponseSet)
                .build();
    }
}