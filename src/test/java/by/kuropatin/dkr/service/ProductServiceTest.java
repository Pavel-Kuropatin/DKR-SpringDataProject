package by.kuropatin.dkr.service;

import by.kuropatin.dkr.exception.ApplicationException;
import by.kuropatin.dkr.model.Product;
import by.kuropatin.dkr.model.ProductColumnName;
import by.kuropatin.dkr.model.request.ProductCreateRequest;
import by.kuropatin.dkr.model.request.ProductSearchRequest;
import by.kuropatin.dkr.model.request.ProductUpdateRequest;
import by.kuropatin.dkr.repository.ProductRepository;
import by.kuropatin.dkr.transformer.ProductTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    private ProductService productService;

    @Autowired
    private ProductTransformer productTransformer;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository, productTransformer);
    }

    @Test
    void getProductByIdTest() {
        final long productId = 13666;
        given(productRepository.findProductById(productId)).willReturn(Optional.of(getProduct()));

        productService.getProductById(productId);

        verify(productRepository).findProductById(productId);
    }

    @Test
    void getProductResponseByIdTest() {
        final long productId = 13666;
        given(productRepository.findProductById(productId)).willReturn(Optional.of(getProduct()));

        productService.getProductResponseById(productId);

        verify(productRepository).findProductById(productId);
    }

    @Test
    void findProductsTest() {
        final ProductSearchRequest request = ProductSearchRequest.builder()
                .pageNo(0)
                .pageSize(10)
                .name("CD")
                .minPrice(BigDecimal.ZERO)
                .maxPrice(BigDecimal.valueOf(1000))
                .sortBy(ProductColumnName.NAME)
                .sortDirection(Sort.Direction.ASC)
                .build();

        given(productRepository.findProducts(request.getName(), request.getMinPrice(), request.getMaxPrice(), PageRequest.of(request.getPageNo(), request.getPageSize(), Sort.by(request.getSortDirection(), request.getSortBy().getColumnName()))))
                .willReturn(new PageImpl<>(new ArrayList<>()));

        productService.findProducts(request);

        verify(productRepository).findProducts(request.getName(), request.getMinPrice(), request.getMaxPrice(), PageRequest.of(request.getPageNo(), request.getPageSize(), Sort.by(request.getSortDirection(), request.getSortBy().getColumnName())));
    }

    @Test
    void getProductByIdThrowsExceptionTest() {
        final long productId = 13666;
        given(productRepository.findProductById(productId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProductResponseById(productId))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining(String.format("Product with id %s was not found", productId));
    }

    @Test
    void createProductTest() {
        final ProductCreateRequest request = getProductCreateRequest();

        productService.createProduct(request);

        verify(productRepository).save(Mockito.any(Product.class));
    }

    @Test
    void updateProductTest() {
        final ProductUpdateRequest request = getProductUpdateRequest();

        productService.updateProduct(request);

        verify(productRepository).save(Mockito.any(Product.class));
    }

    @Test
    void deleteProductTest() {
        final long productId = 13666;
        given(productRepository.existsProductById(productId)).willReturn(true);

        productService.deleteProduct(productId);

        verify(productRepository).deleteById(productId);
    }

    @Test
    void deleteProductThrowsExceptionTest() {
        final long productId = 13666;
        given(productRepository.existsProductById(productId)).willReturn(false);

        assertThatThrownBy(() -> productService.deleteProduct(productId))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining(String.format("Product with id %s was not found", productId));
    }

    @Test
    void isAvailableByIdTest() {
        final long productId = 13666;

        productService.isAvailableById(productId);

        verify(productRepository).isAvailableById(productId);
    }

    private ProductCreateRequest getProductCreateRequest() {
        return ProductCreateRequest.builder()
                .name("name")
                .description("description")
                .price(new BigDecimal("1.23"))
                .build();
    }

    private ProductUpdateRequest getProductUpdateRequest() {
        return ProductUpdateRequest.builder()
                .name("name")
                .description("description")
                .price(new BigDecimal("1.23"))
                .isAvailable(true)
                .build();
    }

    private Product getProduct() {
        return Product.builder()
                .name("name")
                .description("description")
                .price(new BigDecimal("1.23"))
                .build();
    }
}