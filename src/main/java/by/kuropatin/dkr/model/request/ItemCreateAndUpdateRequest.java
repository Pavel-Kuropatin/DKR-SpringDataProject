package by.kuropatin.dkr.model.request;

import by.kuropatin.dkr.util.ToStringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import static by.kuropatin.dkr.util.AppConstants.MAX_ITEM_QUANTITY;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public final class ItemCreateAndUpdateRequest {

    private long productId;

    @Min(value = 1, message = "Minimum quantity is 1")
    @Max(value = MAX_ITEM_QUANTITY, message = "Maximum quantity is 99")
    private int quantity;

    @Override
    public String toString() {
        return ToStringUtils.toJsonString(this, ToStringUtils.JsonStyle.PRETTY);
    }
}