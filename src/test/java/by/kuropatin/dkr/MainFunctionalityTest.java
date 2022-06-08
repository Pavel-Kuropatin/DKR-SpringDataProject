package by.kuropatin.dkr;

import by.kuropatin.dkr.model.Gender;
import by.kuropatin.dkr.model.request.ItemCreateAndUpdateRequest;
import by.kuropatin.dkr.model.request.ProductCreateRequest;
import by.kuropatin.dkr.model.request.UserCreateRequest;
import by.kuropatin.dkr.model.response.ProductResponse;
import by.kuropatin.dkr.model.response.UserResponse;
import by.kuropatin.dkr.service.ItemService;
import by.kuropatin.dkr.service.OrderService;
import by.kuropatin.dkr.service.ProductService;
import by.kuropatin.dkr.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MainFunctionalityTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Test
    @Order(1)
    @Transactional
    @Rollback(value = false)
    void addProducts() {
        // Добавление продуктов
        final ProductResponse productResponse1 = productService.createProduct(getProductCreateRequest(1));
        final ProductResponse productResponse2 = productService.createProduct(getProductCreateRequest(2));
        final ProductResponse productResponse3 = productService.createProduct(getProductCreateRequest(3));
        final ProductResponse productResponse4 = productService.createProduct(getProductCreateRequest(4));
        final ProductResponse productResponse5 = productService.createProduct(getProductCreateRequest(5));
    }

    @Test
    @Order(2)
    @Transactional
    @Rollback(value = false)
    void registerUsers() {
        // Регистрация пользователей
        final UserResponse userResponse1 = userService.createUser(getCreateUserRequest(1));
        final UserResponse userResponse2 = userService.createUser(getCreateUserRequest(2));
    }

    @Test
    @Order(3)
    @Transactional
    @Rollback(value = false)
    void addProductsToCart() {
        // Добавление продуктов в корзину первого пользователя
        final int quantity = 9;
        itemService.addItemToCart(getItemRequest(1, quantity), 1);
        itemService.addItemToCart(getItemRequest(2, quantity), 1);

        // Добавление двух одинаковых продуктов в корзину, при этом должны суммироваться quantity, а не создаваться новый Item
        itemService.addItemToCart(getItemRequest(3, quantity), 1);
        itemService.addItemToCart(getItemRequest(3, quantity), 1);

        itemService.addItemToCart(getItemRequest(4, quantity), 1);
        itemService.addItemToCart(getItemRequest(5, quantity), 1);

        // Добавление продуктов в корзину первого пользователя
        itemService.addItemToCart(getItemRequest(1, quantity), 2);
        itemService.addItemToCart(getItemRequest(3, quantity), 2);
        itemService.addItemToCart(getItemRequest(4, quantity), 2);
        itemService.addItemToCart(getItemRequest(5, quantity), 2);
    }

    @Test
    @Order(4)
    @Transactional
    @Rollback(value = false)
    void deleteProduct() {
        // Удаление 1 продукта из БД, при этом должны быть удалены соответствующие Item
        productService.deleteProduct(1);
    }

    @Test
    @Order(5)
    @Transactional
    @Rollback(value = false)
    void removeProductFromCart() {
        // Удаление 4 продукта из корзины первого пользователя
        itemService.deleteItemFromCart(1, 4);

        // Удаление 5 продукта из корзины второго пользователя
        itemService.deleteItemFromCart(2, 5);
    }

    @Test
    @Order(6)
    @Transactional
    @Rollback(value = false)
    void clearCart() {
        // Очистка корзины второго пользователя
        itemService.clearCartByUserId(2);
    }

    @Test
    @Order(7)
    @Transactional
    @Rollback(value = false)
    void placeOrder() {
        // Оформление заказа первым пользователем
        orderService.placeOrder(1);
    }

    private ProductCreateRequest getProductCreateRequest(final int i) {
        return ProductCreateRequest.builder()
                .name("name_" + i)
                .description("description_" + i)
                .price(new BigDecimal("1.23"))
                .build();
    }

    private UserCreateRequest getCreateUserRequest(final int i) {
        return UserCreateRequest.builder()
                .login("login_" + i)
                .password("password_" + i)
                .name("name_" + i)
                .surname("surname_" + i)
                .gender(Gender.UNDEFINED)
                .birthDate(LocalDate.now().format(DateTimeFormatter.ISO_DATE))
                .phone("+37512345678" + i)
                .email("email_" + i + "@gmail.com")
                .build();
    }

    private ItemCreateAndUpdateRequest getItemRequest(final long productId, final int quantity) {
        return ItemCreateAndUpdateRequest.builder()
                .productId(productId)
                .quantity(quantity)
                .build();
    }
}