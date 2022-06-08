package by.kuropatin.dkr.repository;

import by.kuropatin.dkr.model.Product;
import by.kuropatin.dkr.model.ProductColumnName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void productRepositoryTest() {
        final Product product = Product.builder()
                .name("name")
                .description("description")
                .price(new BigDecimal("1.23"))
                .isAvailable(true)
                .build();

        productRepository.save(product);

        // findProductById()
        assertTrue(productRepository.findProductById(product.getId()).isPresent());
        assertFalse(productRepository.findProductById(0L).isPresent());

        // findProducts()
        final String name = "name";
        final BigDecimal minPrice = null;
        final BigDecimal maxPrice = BigDecimal.valueOf(1000);
        final Pageable paging = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, ProductColumnName.PRICE.getColumnName()));

        final Page<Product> productPage = productRepository.findProducts(name, minPrice, maxPrice, paging);
        assertTrue(productPage.hasContent());
        assertTrue(productPage.getTotalElements() > 0);
        assertTrue(productPage.getTotalPages() > 0);

        // existsById()
        assertTrue(productRepository.existsById(product.getId()));
        assertFalse(productRepository.existsProductById(0L));

        // isAvailableById()
        assertTrue(productRepository.isAvailableById(product.getId()));
        assertFalse(productRepository.isAvailableById(0L));

        // setNotAvailableById()
        productRepository.setNotAvailableById(product.getId());
        assertFalse(productRepository.isAvailableById(product.getId()));

        // setAvailableById()
        productRepository.setAvailableById(product.getId());
        assertTrue(productRepository.isAvailableById(product.getId()));
    }
}