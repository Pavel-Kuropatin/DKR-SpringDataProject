package by.kuropatin.dkr.repository;

import by.kuropatin.dkr.model.Product;
import by.kuropatin.dkr.util.CacheNames;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    @Query(value = "SELECT * FROM dev.products ORDER BY random() LIMIT 1", nativeQuery = true)
    Optional<Product> findRandom();

    @Cacheable(value = CacheNames.PRODUCT, key = "'ProductRepository_findProductById_'+#productId")
    Optional<Product> findProductById(final long productId);

    @Query("SELECT product " +
            "FROM Product product " +
            "WHERE (?1 IS NULL OR LOWER(product.name) LIKE LOWER(CONCAT('%', ?1, '%'))) " +
            "AND (?2 IS NULL OR product.price >= ?2) " +
            "AND (?3 IS NULL OR product.price <= ?3)")
    Page<Product> findProducts(final String name, final BigDecimal minPrice, final BigDecimal maxPrice, final Pageable paging);

    @Cacheable(value = CacheNames.BOOLEAN, key = "'ProductRepository_existsProductById_'+#productId")
    boolean existsProductById(@NonNull final long productId);

    @Cacheable(value = CacheNames.BOOLEAN, key = "'ProductRepository_isAvailableById_'+#productId")
    @Query("SELECT CASE WHEN COUNT(product.id) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Product product " +
            "WHERE product.id = ?1 " +
            "AND product.isAvailable = true")
    boolean isAvailableById(final long productId);

    @Transactional(rollbackFor = SQLException.class)
    @Modifying
    @Query("UPDATE Product product " +
            "SET product.isAvailable = true " +
            "WHERE product.id = ?1")
    void setAvailableById(final long productId);

    @Transactional(rollbackFor = SQLException.class)
    @Modifying
    @Query("UPDATE Product product " +
            "SET product.isAvailable = false " +
            "WHERE product.id = ?1")
    void setNotAvailableById(final long productId);
}