package by.kuropatin.dkr.model.response;

import by.kuropatin.dkr.model.Item;
import by.kuropatin.dkr.model.OrderStatus;
import by.kuropatin.dkr.util.ToStringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public final class OrderResponse {

    private Long id;
    private OrderStatus status;
    private LocalDate orderDate;
    private Set<Item> items;

    @Override
    public String toString() {
        return ToStringUtils.toJsonString(this, ToStringUtils.JsonStyle.PRETTY);
    }
}