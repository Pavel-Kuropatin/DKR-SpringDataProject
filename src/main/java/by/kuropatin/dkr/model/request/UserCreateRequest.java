package by.kuropatin.dkr.model.request;

import by.kuropatin.dkr.util.ToStringUtils;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public final class UserCreateRequest extends UserUpdateRequest {

    @NotBlank(message = "Enter login")
    @Size(min = 2, max = 20, message = "Login should be between 2 and 20 characters")
    private String login;

    @NotBlank(message = "Enter password")
    @Size(min = 8, max = 20, message = "Password should be between 8 and 20 characters")
    private String password;

    @Override
    public String toString() {
        return ToStringUtils.toJsonString(this, ToStringUtils.JsonStyle.PRETTY);
    }
}