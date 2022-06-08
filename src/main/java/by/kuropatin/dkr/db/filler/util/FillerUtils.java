package by.kuropatin.dkr.db.filler.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FillerUtils {

    public static int randomInt(final int minInclusive, final int maxInclusive) {
        return RandomUtils.nextInt(minInclusive, maxInclusive + 1);
    }

    public static BigDecimal randomBigDecimal(final double minInclusive, final double maxInclusive) {
        final double randomDouble = RandomUtils.nextDouble(minInclusive, maxInclusive + 1);
        BigDecimal bigDecimal = BigDecimal.valueOf(randomDouble);
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        return bigDecimal;
    }
}