package by.kuropatin.dkr.model.request;

import by.kuropatin.dkr.util.ToStringUtils;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public final class ProductUpdateRequest extends ProductCreateRequest {

    @NotNull(message = "Specify if product available")
    private Boolean isAvailable;

    @Override
    public String toString() {
        return ToStringUtils.toJsonString(this, ToStringUtils.JsonStyle.PRETTY);
    }
}