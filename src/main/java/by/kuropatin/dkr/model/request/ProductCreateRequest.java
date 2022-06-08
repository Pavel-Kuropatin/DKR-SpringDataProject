package by.kuropatin.dkr.model.request;

import by.kuropatin.dkr.util.ToStringUtils;
import by.kuropatin.dkr.validation.BigDecimalInRange;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ProductCreateRequest {

    @NotBlank(message = "Enter name")
    @Size(min = 1, max = 255, message = "Name should be between 1 and 255 characters")
    private String name;

    @NotBlank(message = "Enter description")
    @Size(min = 1, max = 2000, message = "Description should be between 1 and 2000 characters")
    private String description;

    @BigDecimalInRange(nullable = false, min = 0, max = 100000, message = "Price should be decimal more than 0 and less than 100000")
    private BigDecimal price;

    @Override
    public String toString() {
        return ToStringUtils.toJsonString(this, ToStringUtils.JsonStyle.PRETTY);
    }
}