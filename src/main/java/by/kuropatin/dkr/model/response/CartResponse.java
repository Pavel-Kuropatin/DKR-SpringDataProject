package by.kuropatin.dkr.model.response;

import by.kuropatin.dkr.model.Item;
import by.kuropatin.dkr.util.ToStringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public final class CartResponse {

    private Long id;
    private Set<ItemResponse> items;

    @Override
    public String toString() {
        return ToStringUtils.toJsonString(this, ToStringUtils.JsonStyle.PRETTY);
    }
}