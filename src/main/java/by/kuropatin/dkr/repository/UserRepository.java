package by.kuropatin.dkr.repository;

import by.kuropatin.dkr.model.User;
import by.kuropatin.dkr.util.CacheNames;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "SELECT * FROM dev.users ORDER BY random() LIMIT 1", nativeQuery = true)
    Optional<User> findRandom();

    @Cacheable(value = CacheNames.USER, key = "'UserRepository_findUserById_'+#id")
    Optional<User> findUserById(final long id);

    @Cacheable(value = CacheNames.USER, key = "'UserRepository_findUserByLogin_'+#login")
    Optional<User> findUserByLogin(final String login);

    @Cacheable(value = CacheNames.BOOLEAN, key = "'UserRepository_existsUserById_'+#id")
    boolean existsUserById(final long id);

    @Cacheable(value = CacheNames.BOOLEAN, key = "'UserRepository_existsUserByLogin_'+#login")
    boolean existsUserByLogin(final String login);

    @Cacheable(value = CacheNames.BOOLEAN, key = "'UserRepository_existsUserByPhone_'+#phone")
    boolean existsUserByPhone(final String phone);

    @Cacheable(value = CacheNames.BOOLEAN, key = "'UserRepository_existsUserByEmail_'+#email")
    boolean existsUserByEmail(final String email);
}