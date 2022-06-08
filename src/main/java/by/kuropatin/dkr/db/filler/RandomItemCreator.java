package by.kuropatin.dkr.db.filler;

import by.kuropatin.dkr.db.filler.util.FillerUtils;
import by.kuropatin.dkr.model.request.ItemCreateAndUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RandomItemCreator {

    public ItemCreateAndUpdateRequest randomItem(final long productId) {
        return ItemCreateAndUpdateRequest.builder()
                .productId(productId)
                .quantity(FillerUtils.randomInt(1, 3))
                .build();
    }
}