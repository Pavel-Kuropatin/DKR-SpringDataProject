package by.kuropatin.dkr.transformer;

import by.kuropatin.dkr.model.Product;
import by.kuropatin.dkr.model.request.ProductCreateRequest;
import by.kuropatin.dkr.model.request.ProductUpdateRequest;
import by.kuropatin.dkr.model.response.ProductResponse;
import org.springframework.stereotype.Component;

@Component
public final class ProductTransformer {

    public Product transformProduct(final ProductCreateRequest request) {
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .isAvailable(true)
                .build();
    }

    public Product transformProduct(final ProductUpdateRequest request) {
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .isAvailable(request.getIsAvailable())
                .build();
    }

    public ProductResponse transformProductResponse(final Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .isAvailable(product.getIsAvailable())
                .build();
    }
}