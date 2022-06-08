package by.kuropatin.dkr.service;

import by.kuropatin.dkr.exception.ApplicationException;
import by.kuropatin.dkr.model.Product;
import by.kuropatin.dkr.model.User;
import by.kuropatin.dkr.model.request.ProductCreateRequest;
import by.kuropatin.dkr.model.request.ProductSearchRequest;
import by.kuropatin.dkr.model.request.ProductUpdateRequest;
import by.kuropatin.dkr.model.response.ProductResponse;
import by.kuropatin.dkr.model.response.ProductSearchResponse;
import by.kuropatin.dkr.repository.ProductRepository;
import by.kuropatin.dkr.transformer.ProductTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private static final String NOT_FOUND = "Product with id %s was not found";
    private final ProductRepository repository;
    private final ProductTransformer transformer;

    public Product getRandom() {
        return repository.findRandom()
                .orElseThrow(() -> new ApplicationException("No product found"));
    }

    public Product getProductById(final long productId) {
        return repository.findProductById(productId)
                .orElseThrow(() -> new ApplicationException(NOT_FOUND, productId));
    }

    public ProductResponse getProductResponseById(final long productId) {
        return transformer.transformProductResponse(getProductById(productId));
    }

    public ProductSearchResponse findProducts(final ProductSearchRequest request) {
        final Pageable paging = PageRequest.of(request.getPageNo(), request.getPageSize(), Sort.by(request.getSortBy().getColumnName()));
        final Page<Product> pagedResult = repository.findProducts(request.getName(), request.getMinPrice(), request.getMaxPrice(), paging);

        final List<ProductResponse> products = pagedResult.stream()
                .map(transformer::transformProductResponse)
                .toList();

        return ProductSearchResponse.builder()
                .total(pagedResult.getTotalElements())
                .pages(pagedResult.getTotalPages())
                .products(products)
                .build();
    }

    @Transactional(rollbackFor = Throwable.class)
    public ProductResponse createProduct(@Valid final ProductCreateRequest request) {
        final Product product = transformer.transformProduct(request);
        repository.save(product);
        return transformer.transformProductResponse(product);
    }

    @Transactional(rollbackFor = Throwable.class)
    public ProductResponse updateProduct(@Valid final ProductUpdateRequest request) {
        final Product product = transformer.transformProduct(request);
        repository.save(product);
        return transformer.transformProductResponse(product);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void deleteProduct(final long productId) {
        if (repository.existsProductById(productId)) {
            repository.deleteById(productId);
        } else {
            throw new ApplicationException(NOT_FOUND, productId);
        }
    }

    public boolean isAvailableById(final long productId) {
        return repository.isAvailableById(productId);
    }
}