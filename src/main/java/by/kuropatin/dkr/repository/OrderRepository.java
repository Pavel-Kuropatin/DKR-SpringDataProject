package by.kuropatin.dkr.repository;

import by.kuropatin.dkr.model.Order;
import by.kuropatin.dkr.util.CacheNames;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {

    @Cacheable(value = CacheNames.ORDER, key = "'OrderRepository_findOrdersByUserId_'+#userId")
    List<Order> findOrdersByUserId(final long userId);

    @Cacheable(value = CacheNames.ORDER, key = "'OrderRepository_findOrderByIdAndUserId_'+#orderId+'_'+#userId")
    Optional<Order> findOrderByIdAndUserId(final long orderId, final long userId);
}