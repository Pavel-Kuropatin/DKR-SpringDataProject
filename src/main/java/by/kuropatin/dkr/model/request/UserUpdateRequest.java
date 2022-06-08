package by.kuropatin.dkr.model.request;

import by.kuropatin.dkr.model.Gender;
import by.kuropatin.dkr.util.ToStringUtils;
import by.kuropatin.dkr.validation.Age;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserUpdateRequest {

    @NotBlank(message = "Enter name")
    @Size(min = 2, max = 20, message = "Name should be between 2 and 20 characters")
    private String name;

    @NotBlank(message = "Enter surname")
    @Size(min = 2, max = 20, message = "Surname should be between 2 and 20 characters")
    private String surname;

    @NotBlank(message = "Specify gender")
    private Gender gender;

    @NotBlank(message = "Enter birth date")
    @Age(minAge = 18, message = "Age should be 18+")
    private String birthDate;

    @NotBlank(message = "Enter phone number")
    @Pattern(regexp = "^\\+375\\d{9}$", message = "Phone number should start with +375 code and contain 9 digit number")
    private String phone;

    @NotBlank(message = "Enter email")
    @Email(message = "Email should be valid")
    @Size(max = 50, message = "Email should be 50 characters or less")
    private String email;

    @Override
    public String toString() {
        return ToStringUtils.toJsonString(this, ToStringUtils.JsonStyle.PRETTY);
    }
}