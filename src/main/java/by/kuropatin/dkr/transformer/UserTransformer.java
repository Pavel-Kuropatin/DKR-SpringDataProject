package by.kuropatin.dkr.transformer;

import by.kuropatin.dkr.model.User;
import by.kuropatin.dkr.model.request.UserCreateRequest;
import by.kuropatin.dkr.model.request.UserUpdateRequest;
import by.kuropatin.dkr.model.response.UserResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public final class UserTransformer {

    public UserResponse transformToUserResponse(final User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .gender(user.getGender())
                .birthDate(user.getBirthDate().format(DateTimeFormatter.ISO_DATE))
                .phone(user.getPhone())
                .email(user.getEmail())
                .build();
    }

    public User transformToUser(final UserCreateRequest request) {
        return User.builder()
                .login(request.getLogin())
                .password(request.getPassword())
                .name(request.getName())
                .surname(request.getSurname())
                .gender(request.getGender())
                .birthDate(LocalDate.parse(request.getBirthDate()))
                .phone(request.getPhone())
                .email(request.getEmail())
                .build();
    }

    public void updateUser(final User user, final UserUpdateRequest request) {
        user.toBuilder()
                .name(request.getName())
                .surname(request.getSurname())
                .gender(request.getGender())
                .birthDate(LocalDate.parse(request.getBirthDate()))
                .phone(request.getPhone())
                .email(request.getEmail())
                .build();
    }
}