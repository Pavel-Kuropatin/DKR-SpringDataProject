package by.kuropatin.dkr;

import by.kuropatin.dkr.model.ProductColumnName;
import by.kuropatin.dkr.model.request.ProductSearchRequest;
import by.kuropatin.dkr.model.response.CartResponse;
import by.kuropatin.dkr.model.response.ProductResponse;
import by.kuropatin.dkr.model.response.ProductSearchResponse;
import by.kuropatin.dkr.model.response.UserResponse;
import by.kuropatin.dkr.service.CartService;
import by.kuropatin.dkr.service.ProductService;
import by.kuropatin.dkr.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
@Transactional
class DkrTest {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Test
    void getUserResponseByIdTest() {
        final long id = 1;

        final UserResponse userResponse = userService.getUserResponseByUserId(id);

        assertNotNull(userResponse);
        assertNotNull(userResponse.getId());
        assertNotNull(userResponse.getName());
        assertNotNull(userResponse.getSurname());
        assertNotNull(userResponse.getGender());
        assertNotNull(userResponse.getBirthDate());
        assertNotNull(userResponse.getPhone());
        assertNotNull(userResponse.getEmail());

        log.info("\n" + userResponse);
    }

    @Test
    void getCart() {
        final long userId = 1;

        final CartResponse cartResponse = cartService.getCartResponseByUserId(userId);

        assertNotNull(cartResponse);
        assertNotNull(cartResponse.getId());
        assertNotNull(cartResponse.getItems());
        assertEquals(userId, cartResponse.getId());

        log.info("\n" + cartResponse);
    }

    @Test
    void findProductsTest() {
        final ProductSearchRequest request = ProductSearchRequest.builder()
                .pageNo(0)
                .pageSize(10)
                .name("CD")
                .minPrice(null)
                .maxPrice(BigDecimal.valueOf(160))
                .sortBy(ProductColumnName.PRICE)
                .sortDirection(Sort.Direction.ASC)
                .build();

        final ProductSearchResponse response = productService.findProducts(request);
        log.info("\n" + response);
    }

    @Test
    void findProductByIdTest() {
        final ProductResponse response = productService.getProductResponseById(1);
        log.info("\n" + response);
    }
}