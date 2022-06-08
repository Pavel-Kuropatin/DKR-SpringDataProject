package by.kuropatin.dkr.db.filler;

import by.kuropatin.dkr.db.filler.util.FillerUtils;
import by.kuropatin.dkr.exception.ApplicationException;
import by.kuropatin.dkr.model.Product;
import by.kuropatin.dkr.model.User;
import by.kuropatin.dkr.model.request.ProductCreateRequest;
import by.kuropatin.dkr.model.request.UserCreateRequest;
import by.kuropatin.dkr.service.ItemService;
import by.kuropatin.dkr.service.OrderService;
import by.kuropatin.dkr.service.ProductService;
import by.kuropatin.dkr.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseFiller {

    private final RandomUserCreator randomUserCreator;
    private final RandomProductCreator randomProductCreator;
    private final RandomItemCreator randomItemCreator;
    private final UserService userService;
    private final ProductService productService;
    private final ItemService itemService;
    private final OrderService orderService;

    public void fillDatabase(final int usersCount, final int productsCount, final int ordersCount, final int cartsCount) {
        createUsers(usersCount);
        createProducts(productsCount);
        createOrders(ordersCount);
        fillCarts(cartsCount);
    }

    private void createUsers(final int usersAmount) {
        int createdUsers = 0;
        while (createdUsers < usersAmount) {
            final UserCreateRequest userCreateRequest = randomUserCreator.randomRequest();
            try {
                userService.createUser(userCreateRequest);
                createdUsers++;
            } catch (ApplicationException e) {
                log.debug(e.getMessage());
            }
        }
    }

    private void createProducts(final int productsAmount) {
        int createdProducts = 0;
        while (createdProducts < productsAmount) {
            final ProductCreateRequest productCreateRequest = randomProductCreator.randomProduct();
            try {
                productService.createProduct(productCreateRequest);
                createdProducts++;
            } catch (ApplicationException e) {
                log.debug(e.getMessage());
            }
        }
    }

    private void createOrders(final int ordersAmount) {
        int createdOrders = 0;
        while (createdOrders < ordersAmount) {
            final Product product;
            final User user;

            try {
                product = productService.getRandom();
                user = userService.getRandom();
            } catch (ApplicationException e) {
                log.debug(e.getMessage());
                continue;
            }

            try {
                for (int i = 0; i < FillerUtils.randomInt(3, 5); i++) {
                    itemService.addItemToCart(randomItemCreator.randomItem(product.getId()), user.getId());
                }
                orderService.placeOrder(user.getId());
                createdOrders++;
            } catch (ApplicationException e) {
                log.debug(e.getMessage());
            }
        }
    }

    private void fillCarts(final int cartsAmount) {
        int filledCarts = 0;
        while (filledCarts < cartsAmount) {
            final Product product;
            final User user;

            try {
                product = productService.getRandom();
                user = userService.getRandom();
            } catch (ApplicationException e) {
                log.debug(e.getMessage());
                continue;
            }

            try {
                for (int i = 0; i < FillerUtils.randomInt(3, 8); i++) {
                    itemService.addItemToCart(randomItemCreator.randomItem(product.getId()), user.getId());
                }
                filledCarts++;
            } catch (ApplicationException e) {
                log.debug(e.getMessage());
            }
        }
    }
}