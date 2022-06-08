package by.kuropatin.dkr.service;

import by.kuropatin.dkr.exception.ApplicationException;
import by.kuropatin.dkr.model.Cart;
import by.kuropatin.dkr.model.Gender;
import by.kuropatin.dkr.model.User;
import by.kuropatin.dkr.repository.CartRepository;
import by.kuropatin.dkr.transformer.CartTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;
    private CartService cartService;

    @Autowired
    private CartTransformer cartTransformer;

    @BeforeEach
    void setUp() {
        cartService = new CartService(cartRepository, cartTransformer);
    }

    @Test
    void findCartByUserIdTest() {
        final long userId = 1;
        given(cartRepository.findCartByUserId(userId)).willReturn(Optional.of(getCart()));

        cartService.getCartResponseByUserId(userId);

        verify(cartRepository).findCartByUserId(userId);
    }

    @Test
    void findUserByIdThrowsExceptionTest() {
        final long userId = 13666;
        given(cartRepository.findCartByUserId(userId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> cartService.getCartResponseByUserId(userId))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining(String.format("Cart of user with id %s was not found", userId));
    }

    private User getUser() {
        return User.builder()
                .id(1L)
                .login("login")
                .password("password")
                .name("name")
                .surname("surname")
                .gender(Gender.UNDEFINED)
                .birthDate(LocalDate.now())
                .phone("+375123456789")
                .email("email@gmail.com")
                .build();
    }

    private Cart getCart() {
        return Cart.builder()
                .id(1L)
                .user(getUser())
                .items(Collections.emptySet())
                .build();
    }
}