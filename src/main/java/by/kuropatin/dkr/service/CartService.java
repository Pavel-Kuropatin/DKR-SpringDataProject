package by.kuropatin.dkr.service;

import by.kuropatin.dkr.exception.ApplicationException;
import by.kuropatin.dkr.model.Cart;
import by.kuropatin.dkr.model.response.CartResponse;
import by.kuropatin.dkr.repository.CartRepository;
import by.kuropatin.dkr.transformer.CartTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository repository;
    private final CartTransformer transformer;

    public Cart getCartByUserId(final long userId) {
        return repository.findCartByUserId(userId)
                .orElseThrow(() -> new ApplicationException("Cart of user with id %s was not found", userId));
    }

    public CartResponse getCartResponseByUserId(final long userId) {
        return transformer.transformToCartResponse(getCartByUserId(userId));
    }
}