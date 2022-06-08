package by.kuropatin.dkr;

import by.kuropatin.dkr.config.CacheConfig;
import by.kuropatin.dkr.db.filler.DatabaseFiller;
import by.kuropatin.dkr.db.filler.RandomProductCreator;
import by.kuropatin.dkr.db.filler.RandomUserCreator;
import by.kuropatin.dkr.db.filler.Vocabulary;
import by.kuropatin.dkr.repository.CartRepository;
import by.kuropatin.dkr.repository.ItemRepository;
import by.kuropatin.dkr.repository.ProductRepository;
import by.kuropatin.dkr.repository.UserRepository;
import by.kuropatin.dkr.service.CartService;
import by.kuropatin.dkr.service.ItemService;
import by.kuropatin.dkr.service.ProductService;
import by.kuropatin.dkr.service.UserService;
import by.kuropatin.dkr.transformer.CartTransformer;
import by.kuropatin.dkr.transformer.ProductTransformer;
import by.kuropatin.dkr.transformer.UserTransformer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class DkrApplicationTests {

    @Autowired
    private CacheConfig cacheConfig;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartTransformer cartTransformer;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductTransformer productTransformer;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserTransformer userTransformer;

    @Autowired
    private DatabaseFiller databaseFiller;

    @Autowired
    private RandomUserCreator randomUserCreator;

    @Autowired
    private RandomProductCreator randomProductCreator;

    @Autowired
    private Vocabulary vocabulary;

    @Test
    void contextLoads() {
        assertNotNull(cacheConfig);

        assertNotNull(cartRepository);
        assertNotNull(cartService);
        assertNotNull(cartTransformer);

        assertNotNull(itemRepository);
        assertNotNull(itemService);

        assertNotNull(productRepository);
        assertNotNull(productService);
        assertNotNull(productTransformer);

        assertNotNull(userRepository);
        assertNotNull(userService);
        assertNotNull(userTransformer);

        assertNotNull(databaseFiller);
        assertNotNull(randomUserCreator);
        assertNotNull(randomProductCreator);
        assertNotNull(vocabulary);
    }
}