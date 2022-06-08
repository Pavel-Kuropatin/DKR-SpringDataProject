package by.kuropatin.dkr.model.response;

import by.kuropatin.dkr.util.ToStringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public final class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean isAvailable;

    @Override
    public String toString() {
        return ToStringUtils.toJsonString(this, ToStringUtils.JsonStyle.PRETTY);
    }
}