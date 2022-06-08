package by.kuropatin.dkr.db.filler;

import by.kuropatin.dkr.db.filler.util.FillerUtils;
import by.kuropatin.dkr.model.request.ProductCreateRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class RandomProductCreator {

    private final Vocabulary vocabulary;

    public ProductCreateRequest randomProduct() {
        return ProductCreateRequest.builder()
                .name(getName())
                .description("Описание товара")
                .price(getPrice())
                .build();
    }

    private String getName() {
        return vocabulary.getComputerHardware() + " " + RandomStringUtils.randomAlphabetic(3).toUpperCase();
    }

    private BigDecimal getPrice() {
        return FillerUtils.randomBigDecimal(49.99, 499.99);
    }
}