package by.kuropatin.dkr.repository;

import by.kuropatin.dkr.model.Cart;
import by.kuropatin.dkr.util.CacheNames;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartRepository extends CrudRepository<Cart, Long> {

    @Cacheable(value = CacheNames.CART, key = "'CartRepository_findCartByUserId_'+#userId")
    @EntityGraph(attributePaths = {"user", "items", "items.product"})
//    @Query("SELECT cart " +
//            "FROM Cart cart " +
//            "INNER JOIN FETCH User user ON user.cart.id = cart.id " +
//            "WHERE user.id = ?1")
    Optional<Cart> findCartByUserId(final long userId);
}