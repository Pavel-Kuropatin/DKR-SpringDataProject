package by.kuropatin.dkr.service;

import by.kuropatin.dkr.exception.ApplicationException;
import by.kuropatin.dkr.model.Cart;
import by.kuropatin.dkr.model.Item;
import by.kuropatin.dkr.model.Product;
import by.kuropatin.dkr.model.request.ItemCreateAndUpdateRequest;
import by.kuropatin.dkr.repository.ItemRepository;
import by.kuropatin.dkr.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository repository;
    private final ProductService productService;
    private final CartService cartService;

    public Item getItemById(final long itemId) {
        return repository.findItemById(itemId)
                .orElseThrow(() -> new ApplicationException("Item with id %s was not found", itemId));
    }

    public Item getItemByCartIdAndProductId(final long cartId, long productId) {
        return repository.findItemByCartIdAndProductId(cartId, productId)
                .orElse(null);
    }

    @Transactional(rollbackFor = Throwable.class)
    public Item addItemToCart(@Valid final ItemCreateAndUpdateRequest request, final long userId) {
        final Product product = productService.getProductById(request.getProductId());
        final Cart cart = cartService.getCartByUserId(userId);
        Item item = getItemByCartIdAndProductId(cart.getId(), request.getProductId());

        if (item == null) {
            item = Item.builder()
                    .product(product)
                    .quantity(request.getQuantity())
                    .cart(cart)
                    .order(null)
                    .build();
        } else {
            updateQuantity(request, item);
        }

        return repository.save(item);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void deleteItemFromCart(final long userId, final long productId) {
        final Cart cart = cartService.getCartByUserId(userId);
        repository.deleteByProductIdAndCartId(productId, cart.getId());
    }

    private void updateQuantity(final ItemCreateAndUpdateRequest request, final Item item) {
        final int newQuantity = item.getQuantity() + request.getQuantity();
        if (newQuantity > AppConstants.MAX_ITEM_QUANTITY) {
            throw new ApplicationException("Maximum quantity is %s", AppConstants.MAX_ITEM_QUANTITY);
        }
        item.setQuantity(newQuantity);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void clearCartByUserId(final long userId) {
        final Cart cart = cartService.getCartByUserId(userId);
        repository.deleteItemsByCartId(cart.getId());
    }
}