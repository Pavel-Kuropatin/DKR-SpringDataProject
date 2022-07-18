package by.kuropatin.dkr.repository;

import by.kuropatin.dkr.model.Cart;
import by.kuropatin.dkr.model.Gender;
import by.kuropatin.dkr.model.Item;
import by.kuropatin.dkr.model.Product;
import by.kuropatin.dkr.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void itemRepositoryTest() {
        final Product product = Product.builder()
                .name("name")
                .description("description")
                .price(new BigDecimal("1.23"))
                .isAvailable(true)
                .build();

        productRepository.save(product);

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

        final Item item = Item.builder()
                .product(product)
                .cart(cart)
                .quantity(1)
                .build();

        itemRepository.save(item);

        // findItemById()
        assertTrue(itemRepository.findItemById(item.getId()).isPresent());
        assertFalse(itemRepository.findItemById(0).isPresent());

        // findItemByCartIdAndProductId()
        assertTrue(itemRepository.findItemByCartIdAndProductId(cart.getId(), product.getId()).isPresent());
        assertFalse(itemRepository.findItemByCartIdAndProductId(0, 0).isPresent());

        // existsItemById()
        assertTrue(itemRepository.existsItemById(item.getId()));
        assertFalse(itemRepository.existsItemById(0));

        // deleteByProductIdAndCartId()
        itemRepository.deleteByProductIdAndCartId(product.getId(), cart.getId());
        assertFalse(itemRepository.existsItemById(item.getId()));
    }
}