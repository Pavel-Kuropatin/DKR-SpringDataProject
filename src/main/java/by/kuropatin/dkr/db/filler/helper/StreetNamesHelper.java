package by.kuropatin.dkr.db.filler.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
public class StreetNamesHelper {

    public static void main(String[] args) throws IOException {
        final File file = new File("src/main/resources/vocabulary/streets.txt");

        final List<String> streetNames = FileUtils.readLines(file, StandardCharsets.UTF_8);
        final List<String> processedStreetNames = streetNames.stream()
                .map(streetName -> streetName.replaceAll(", белор.+$", ""))
                .filter(streetName -> !streetName.isEmpty())
                .toList();

        final List<String> refactoredStreetNames = processedStreetNames.stream()
                .map(StreetNamesHelper::getProperString)
                .toList();

        refactoredStreetNames.forEach(log::info);

        final StringBuilder sb = new StringBuilder();
        refactoredStreetNames.forEach(s -> sb.append(s).append("\n"));
        FileUtils.writeStringToFile(file, sb.toString(), StandardCharsets.UTF_8);
    }

    private static String getProperString(final String s) {
        if (s.endsWith(" улица")) {
            return "ул. " + s.replace(" улица", "");
        } else if (s.endsWith(" переулок")) {
            return "пер. " + s.replace(" переулок", "");
        } else if (s.endsWith(" площадь")) {
            return "пл. " + s.replace(" площадь", "");
        } else if (s.endsWith(" проспект")) {
            return "пр. " + s.replace(" проспект", "");
        } else if (s.endsWith(" проезд")) {
            return "проезд " + s.replace(" проезд", "");
        } else {
            return s;
        }
    }
}