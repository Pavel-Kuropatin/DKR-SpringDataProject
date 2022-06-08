package by.kuropatin.dkr.util;

import by.kuropatin.dkr.exception.ApplicationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CacheNames {

    public static final String ADDRESS = "address";
    public static final String BOOLEAN = "boolean";
    public static final String CART = "cart";
    public static final String ITEM = "item";
    public static final String ORDER = "order";
    public static final String PRODUCT = "product";
    public static final String USER = "user";

    public static String[] getCacheNames() {
        try {
            final Field[] fields = CacheNames.class.getDeclaredFields();
            final String[] cacheNames = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                cacheNames[i] = (String) fields[i].get(fields[i].getName());
            }
            return cacheNames;
        } catch (IllegalAccessException e) {
            throw new ApplicationException(e);
        }
    }
}