package by.kuropatin.dkr.model.request;

import by.kuropatin.dkr.model.ProductColumnName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public final class ProductSearchRequest {

    private String name;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private int pageNo;
    private int pageSize;
    private ProductColumnName sortBy;
    private Sort.Direction sortDirection;
}