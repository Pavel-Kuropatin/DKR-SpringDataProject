package by.kuropatin.dkr.model.response;

import by.kuropatin.dkr.util.ToStringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public final class ProductSearchResponse {

    private long total;
    private long pages;
    private List<ProductResponse> products;

    @Override
    public String toString() {
        return ToStringUtils.toJsonString(this, ToStringUtils.JsonStyle.PRETTY);
    }
}