package by.kuropatin.dkr.model.response;

import by.kuropatin.dkr.model.Gender;
import by.kuropatin.dkr.util.ToStringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public final class UserResponse {

    private Long id;
    private String name;
    private String surname;
    private Gender gender;
    private String birthDate;
    private String phone;
    private String email;

    @Override
    public String toString() {
        return ToStringUtils.toJsonString(this, ToStringUtils.JsonStyle.PRETTY);
    }
}