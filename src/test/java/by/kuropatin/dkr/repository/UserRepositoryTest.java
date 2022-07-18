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
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void userRepositoryTest() {
        final User user = User.builder()
                .login("login")
                .password("password")
                .name("name")
                .surname("surname")
                .gender(Gender.UNDEFINED)
                .birthDate(LocalDate.now())
                .phone("+375123456789")
                .email("email")
                .build();

        final Cart cart = Cart.builder()
                .user(user)
                .build();

        user.setCart(cart);

        userRepository.save(user);

        // findUserById()
        assertTrue(userRepository.findUserById(user.getId()).isPresent());
        assertFalse(userRepository.findUserById(0).isPresent());

        // findUserByLogin()
        assertTrue(userRepository.findUserByLogin(user.getLogin()).isPresent());
        assertFalse(userRepository.findUserByLogin("").isPresent());

        // existsById()
        assertTrue(userRepository.existsById(user.getId()));
        assertFalse(userRepository.existsUserById(0));

        // existsByLogin()
        assertTrue(userRepository.existsUserByLogin(user.getLogin()));
        assertFalse(userRepository.existsUserByLogin(""));

        // existsByEmail()
        assertTrue(userRepository.existsUserByPhone(user.getPhone()));
        assertFalse(userRepository.existsUserByPhone(""));

        // existsByEmail()
        assertTrue(userRepository.existsUserByEmail(user.getEmail()));
        assertFalse(userRepository.existsUserByEmail(""));
    }
}