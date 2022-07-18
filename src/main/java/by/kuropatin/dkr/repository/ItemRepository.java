package by.kuropatin.dkr.repository;

import by.kuropatin.dkr.model.Item;
import by.kuropatin.dkr.util.CacheNames;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

public interface ItemRepository extends CrudRepository<Item, Long> {

    @Cacheable(value = CacheNames.ITEM, key = "'ItemRepository_findItemById_'+#id")
    @EntityGraph(attributePaths = {"product"})
    Optional<Item> findItemById(final long id);

    @Cacheable(value = CacheNames.ITEM, key = "'ItemRepository_findItemByCartIdAndProductId_'+#cartId+'_'+#productId")
    @EntityGraph(attributePaths = {"product"})
    Optional<Item> findItemByCartIdAndProductId(final long cartId, final long productId);

    @Cacheable(value = CacheNames.BOOLEAN, key = "'ItemRepository_existsItemById_'+#id")
    boolean existsItemById(final long id);

    @Transactional(rollbackFor = SQLException.class)
    void deleteByProductIdAndCartId(final long productId, final long cartId);

    @Transactional(rollbackFor = SQLException.class)
    void deleteItemsByCartId(final long cartId);
}