package by.kuropatin.dkr.service;

import by.kuropatin.dkr.exception.ApplicationException;
import by.kuropatin.dkr.model.Gender;
import by.kuropatin.dkr.model.User;
import by.kuropatin.dkr.model.request.UserCreateRequest;
import by.kuropatin.dkr.model.request.UserUpdateRequest;
import by.kuropatin.dkr.repository.UserRepository;
import by.kuropatin.dkr.transformer.UserTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private UserService userService;

    @Autowired
    private UserTransformer userTransformer;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, userTransformer);
    }

    @Test
    void findUserByIdTest() {
        final long id = 13666;
        given(userRepository.findUserById(id)).willReturn(Optional.of(getUser()));

        userService.getUserResponseByUserId(id);

        verify(userRepository).findUserById(id);
    }

    @Test
    void findUserByIdThrowsExceptionTest() {
        final long id = 13666;
        given(userRepository.findUserById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserResponseByUserId(id))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining(String.format("User with id = %s was not found", id));
    }

    @Test
    void findUserByLoginTest() {
        final String login = "login";
        given(userRepository.findUserByLogin(login)).willReturn(Optional.of(getUser()));
        userService.getUserResponseByLogin(login);
        verify(userRepository).findUserByLogin(login);
    }

    @Test
    void findUserByLoginThrowsExceptionTest() {
        final String login = "login";
        given(userRepository.findUserByLogin(login)).willReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserResponseByLogin(login))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining(String.format("User with login %s was not found", login));
    }

    @Test
    void createUserTest() {
        final UserCreateRequest request = getUserCreateRequest();
        given(userRepository.existsUserByLogin(request.getLogin())).willReturn(false);
        given(userRepository.existsUserByPhone(request.getPhone())).willReturn(false);
        given(userRepository.existsUserByEmail(request.getEmail())).willReturn(false);

        userService.createUser(request);

        verify(userRepository).existsUserByLogin(request.getLogin());
        verify(userRepository).existsUserByPhone(request.getPhone());
        verify(userRepository).existsUserByEmail(request.getEmail());
    }

    @Test
    void createUserThrowsExceptionWhenLoginInUseTest() {
        final UserCreateRequest request = getUserCreateRequest();
        given(userRepository.existsUserByLogin(request.getLogin())).willReturn(true);

        assertThatThrownBy(() -> userService.createUser(request))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining(String.format("Login %s already in use", request.getLogin()));
    }

    @Test
    void createUserThrowsExceptionWhenPhoneInUseTest() {
        final UserCreateRequest request = getUserCreateRequest();
        given(userRepository.existsUserByPhone(request.getPhone())).willReturn(true);

        assertThatThrownBy(() -> userService.createUser(request))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining(String.format("Phone %s already in use", request.getPhone()));
    }

    @Test
    void createUserThrowsExceptionWhenEmailInUseTest() {
        final UserCreateRequest request = getUserCreateRequest();
        given(userRepository.existsUserByEmail(request.getEmail())).willReturn(true);

        assertThatThrownBy(() -> userService.createUser(request))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining(String.format("Email %s already in use", request.getEmail()));
    }

    @Test
    void updateUserTest() {
        final long id = 13666;
        final UserUpdateRequest request = getUserUpdateRequest();
        given(userRepository.findUserById(id)).willReturn(Optional.of(getUser()));
        given(userRepository.existsUserByPhone(request.getPhone())).willReturn(false);
        given(userRepository.existsUserByEmail(request.getEmail())).willReturn(false);

        userService.updateUser(request, id);

        verify(userRepository).findUserById(id);
        verify(userRepository).existsUserByPhone(request.getPhone());
        verify(userRepository).existsUserByEmail(request.getEmail());
    }

    @Test
    void updateUserThrowsExceptionWhenUserWasNotFoundTest() {
        final long id = 13666;
        final UserUpdateRequest request = getUserUpdateRequest();
        given(userRepository.findUserById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> userService.updateUser(request, id))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining(String.format("User with id = %s was not found", id));
    }

    @Test
    void updateUserThrowsExceptionWhenPhoneInUseTest() {
        final long id = 13666;
        final UserUpdateRequest request = getUserUpdateRequest();
        given(userRepository.findUserById(id)).willReturn(Optional.of(new User()));
        given(userRepository.existsUserByPhone(request.getPhone())).willReturn(true);

        assertThatThrownBy(() -> userService.updateUser(request, id))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining(String.format("Phone %s already in use", request.getPhone()));
    }

    @Test
    void updateUserThrowsExceptionWhenEmailInUseTest() {
        final long id = 13666;
        final UserUpdateRequest request = getUserUpdateRequest();
        given(userRepository.findUserById(id)).willReturn(Optional.of(new User()));
        given(userRepository.existsUserByEmail(request.getEmail())).willReturn(true);

        assertThatThrownBy(() -> userService.updateUser(request, id))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining(String.format("Email %s already in use", request.getEmail()));
    }

    @Test
    void deleteByIdTest() {
        final long id = 666;
        userService.deleteById(id);
        verify(userRepository).deleteById(id);
    }

    private UserCreateRequest getUserCreateRequest() {
        return UserCreateRequest.builder()
                .login("login")
                .password("password")
                .name("name")
                .surname("surname")
                .gender(Gender.UNDEFINED)
                .birthDate(LocalDate.now().format(DateTimeFormatter.ISO_DATE))
                .phone("+375123456789")
                .email("email@gmail.com")
                .build();
    }

    private UserUpdateRequest getUserUpdateRequest() {
        return UserUpdateRequest.builder()
                .name("name")
                .surname("surname")
                .gender(Gender.UNDEFINED)
                .birthDate(LocalDate.now().format(DateTimeFormatter.ISO_DATE))
                .phone("+375123456789")
                .email("email@gmail.com")
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