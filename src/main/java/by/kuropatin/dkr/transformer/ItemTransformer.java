package by.kuropatin.dkr.transformer;

import by.kuropatin.dkr.model.Item;
import by.kuropatin.dkr.model.response.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class ItemTransformer {

    private final ProductTransformer productTransformer;

    public ItemResponse transformToItemResponse(final Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .quantity(item.getQuantity())
                .product(productTransformer.transformProductResponse(item.getProduct()))
                .build();
    }
}