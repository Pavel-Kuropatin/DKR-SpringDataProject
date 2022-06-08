package by.kuropatin.dkr.db.filler.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class CityNamesHelper {

    public static void main(String[] args) throws IOException {
        final File file = new File("src/main/resources/vocabulary/cities.txt");

        final List<String> cityNames = FileUtils.readLines(file, StandardCharsets.UTF_8);
        cityNames.sort(Comparator.naturalOrder());

        cityNames.forEach(log::info);

        final StringBuilder sb = new StringBuilder();
        cityNames.forEach(s -> sb.append(s).append("\n"));
        FileUtils.writeStringToFile(file, sb.toString(), StandardCharsets.UTF_8);
    }
}