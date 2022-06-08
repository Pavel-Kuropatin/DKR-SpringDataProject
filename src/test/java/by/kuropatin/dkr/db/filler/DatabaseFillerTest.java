package by.kuropatin.dkr.db.filler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatNoException;

@SpringBootTest
class DatabaseFillerTest {

    @Autowired
    private DatabaseFiller databaseFiller;

    @Test
    void fillDatabase() {
        final int usersCount = 10_000;
        final int productsCount = 1_000_000;
        final int ordersCount = 100_000;
        final int cartsCount = 1_000;
        assertThatNoException()
                .isThrownBy(() -> databaseFiller.fillDatabase(usersCount, productsCount, ordersCount, cartsCount));
    }
}