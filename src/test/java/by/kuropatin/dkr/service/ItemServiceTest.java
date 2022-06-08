package by.kuropatin.dkr.service;

import by.kuropatin.dkr.exception.ApplicationException;
import by.kuropatin.dkr.model.Cart;
import by.kuropatin.dkr.model.Gender;
import by.kuropatin.dkr.model.Item;
import by.kuropatin.dkr.model.Product;
import by.kuropatin.dkr.model.User;
import by.kuropatin.dkr.model.request.ItemCreateAndUpdateRequest;
import by.kuropatin.dkr.repository.ItemRepository;
import by.kuropatin.dkr.util.AppConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private ProductService productService;
    @Mock
    private CartService cartService;
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        itemService = new ItemService(itemRepository, productService, cartService);
    }

    @Test
    void getItemByIdTest() {
        final long itemId = 1;

        given(itemRepository.findItemById(itemId)).willReturn(Optional.of(getItem()));

        itemService.getItemById(itemId);

        verify(itemRepository).findItemById(itemId);
    }

    @Test
    void getItemByIdThrowsExceptionTest() {
        final long itemId = 1;

        given(itemRepository.findItemById(itemId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> itemService.getItemById(itemId))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining(String.format("Item with id %s was not found", itemId));
    }

    @Test
    void addItemToCartTest() {
        final long productId = 1;
        final int quantity = 3;
        final ItemCreateAndUpdateRequest request = ItemCreateAndUpdateRequest.builder()
                .productId(productId)
                .quantity(quantity)
                .build();
        final long userId = 1;

        given(productService.getProductById(productId)).willReturn(getProduct());
        given(cartService.getCartByUserId(userId)).willReturn(getCart());
        given(itemRepository.findItemByCartIdAndProductId(getCart().getId(), getProduct().getId())).willReturn(Optional.of(getItem()));

        itemService.addItemToCart(request, userId);

        verify(productService).getProductById(productId);
    }

    @Test
    void addItemToCartThrowsExceptionTest() {
        final long productId = 1;
        final int quantity = 99;
        final ItemCreateAndUpdateRequest request = ItemCreateAndUpdateRequest.builder()
                .productId(productId)
                .quantity(quantity)
                .build();
        final long userId = 1;

        given(productService.getProductById(productId)).willReturn(getProduct());
        given(cartService.getCartByUserId(userId)).willReturn(getCart());
        given(itemRepository.findItemByCartIdAndProductId(getCart().getId(), getProduct().getId())).willReturn(Optional.of(getItem()));

        assertThatThrownBy(() -> itemService.addItemToCart(request, userId))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining(String.format("Maximum quantity is %s", AppConstants.MAX_ITEM_QUANTITY));
    }

    @Test
    void addItemToCartWhenItemNotFoundInCartTest() {
        final long productId = 1;
        final int quantity = 3;
        final ItemCreateAndUpdateRequest request = ItemCreateAndUpdateRequest.builder()
                .productId(productId)
                .quantity(quantity)
                .build();
        final long userId = 1;

        given(productService.getProductById(productId)).willReturn(getProduct());
        given(cartService.getCartByUserId(userId)).willReturn(getCart());
        given(itemRepository.findItemByCartIdAndProductId(getCart().getId(), getProduct().getId())).willReturn(Optional.empty());

        itemService.addItemToCart(request, userId);

        verify(productService).getProductById(productId);
    }

    @Test
    void deleteItemFromCartTest() {
        final long userId = 1;
        final long productId = 1;

        given(cartService.getCartByUserId(userId)).willReturn(getCart());

        itemService.deleteItemFromCart(userId, productId);

        verify(cartService).getCartByUserId(userId);
        verify(itemRepository).deleteByProductIdAndCartId(productId, getCart().getId());
    }

    @Test
    void clearCartByUserIdShouldNotClearTest() {
        final long userId = 13666;
        given(cartService.getCartByUserId(userId)).willReturn(getCart());

        itemService.clearCartByUserId(userId);

        verify(itemRepository).deleteItemsByCartId(getCart().getId());
    }

    private Item getItem() {
        return Item.builder()
                .id(1L)
                .product(getProduct())
                .quantity(3)
                .build();
    }

    private Product getProduct() {
        return Product.builder()
                .id(1L)
                .name("name")
                .description("description")
                .price(new BigDecimal("1.23"))
                .isAvailable(true)
                .build();
    }

    private Cart getCart() {
        return Cart.builder()
                .id(1L)
                .user(getUser())
                .items(null)
                .build();
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
}