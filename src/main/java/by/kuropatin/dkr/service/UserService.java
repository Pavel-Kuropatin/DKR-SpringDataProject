package by.kuropatin.dkr.service;

import by.kuropatin.dkr.exception.ApplicationException;
import by.kuropatin.dkr.model.Cart;
import by.kuropatin.dkr.model.User;
import by.kuropatin.dkr.model.request.UserCreateRequest;
import by.kuropatin.dkr.model.request.UserUpdateRequest;
import by.kuropatin.dkr.model.response.UserResponse;
import by.kuropatin.dkr.repository.UserRepository;
import by.kuropatin.dkr.transformer.UserTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserTransformer transformer;

    public User getRandom() {
        return repository.findRandom()
                .orElseThrow(() -> new ApplicationException("No user found"));
    }

    public User getUserById(final long id) {
        return repository.findUserById(id)
                .orElseThrow(() -> new ApplicationException("User with id = %s was not found", id));
    }

    public UserResponse getUserResponseByUserId(final long id) {
        final User user = getUserById(id);
        return transformer.transformToUserResponse(user);
    }

    public UserResponse getUserResponseByLogin(final String login) {
        final User user = repository.findUserByLogin(login)
                .orElseThrow(() -> new ApplicationException("User with login %s was not found", login));
        return transformer.transformToUserResponse(user);
    }

    @Transactional(rollbackFor = Throwable.class)
    public UserResponse createUser(@Valid final UserCreateRequest request) {
        checkIfLoginInUse(request.getLogin());
        checkIfPhoneInUse(request.getPhone());
        checkIfEmailInUse(request.getEmail());
        final User user = transformer.transformToUser(request);
        final Cart cart = Cart.builder()
                .user(user)
                .build();
        user.setCart(cart);
        repository.save(user);
        return transformer.transformToUserResponse(user);
    }

    @Transactional(rollbackFor = Throwable.class)
    public UserResponse updateUser(@Valid final UserUpdateRequest request, final long userId) {
        final User user = getUserById(userId);
        checkIfPhoneInUse(request.getPhone());
        checkIfEmailInUse(request.getEmail());
        transformer.updateUser(user, request);
        repository.save(user);
        return transformer.transformToUserResponse(user);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void deleteById(final long id) {
        repository.deleteById(id);
    }

    private void checkIfLoginInUse(final String login) {
        if (repository.existsUserByLogin(login)) {
            throw new ApplicationException("Login %s already in use", login);
        }
    }

    private void checkIfPhoneInUse(final String phone) {
        if (repository.existsUserByPhone(phone)) {
            throw new ApplicationException("Phone %s already in use", phone);
        }
    }

    private void checkIfEmailInUse(final String email) {
        if (repository.existsUserByEmail(email)) {
            throw new ApplicationException("Email %s already in use", email);
        }
    }
}