package by.kuropatin.dkr.repository;

import by.kuropatin.dkr.model.Cart;
import by.kuropatin.dkr.model.Gender;
import by.kuropatin.dkr.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void cartRepositoryTest() {
        final User user = User.builder()
                .login("login")
                .password("password")
                .name("name")
                .surname("surname")
                .gender(Gender.UNDEFINED)
                .birthDate(LocalDate.now())
                .phone("+375123456789")
                .email("email@gmail.com")
                .build();

        final Cart cart = Cart.builder()
                .user(user)
                .build();

        user.setCart(cart);

        userRepository.save(user);

        // findCartByUserId()
        assertTrue(cartRepository.findCartByUserId(user.getId()).isPresent());
        assertFalse(cartRepository.findCartByUserId(0).isPresent());
    }
}