package by.kuropatin.dkr.model;

import lombok.Getter;

@Getter
public enum ProductColumnName {

    NAME("name"),
    PRICE("price");

    private final String columnName;

    ProductColumnName(final String columnName) {
        this.columnName = columnName;
    }
}