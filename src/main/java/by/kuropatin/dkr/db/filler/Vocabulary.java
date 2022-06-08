package by.kuropatin.dkr.db.filler;

import by.kuropatin.dkr.db.filler.util.FillerUtils;
import by.kuropatin.dkr.exception.ApplicationException;
import by.kuropatin.dkr.model.Gender;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Component
public class Vocabulary {

    private final List<String> maleNames = loadVocabulary("male_names");
    private final List<String> femaleNames = loadVocabulary("female_names");
    private final List<String> surnames = loadVocabulary("surnames");
    private final List<String> mailServices = loadVocabulary("mail_services");
    private final List<String> computerHardware = loadVocabulary("computer_hardware");
    private final List<String> countries = loadVocabulary("countries");
    private final List<String> cities = loadVocabulary("cities");
    private final List<String> streets = loadVocabulary("streets");

    public String getName(final Gender gender) {
        if (gender == Gender.MALE) {
            return maleNames.get(RandomUtils.nextInt(0, maleNames.size()));
        } else if (gender == Gender.FEMALE) {
            return femaleNames.get(RandomUtils.nextInt(0, femaleNames.size()));
        } else {
            return FillerUtils.randomInt(0, 1) == 0 ? getName(Gender.MALE) : getName(Gender.FEMALE);
        }
    }

    public String getSurname(final Gender gender) {
        if (gender == Gender.MALE) {
            return surnames.get(RandomUtils.nextInt(0, surnames.size()));
        } else if (gender == Gender.FEMALE) {
            return surnames.get(RandomUtils.nextInt(0, surnames.size())) + "a";
        } else {
            return FillerUtils.randomInt(0, 1) == 0 ? getSurname(Gender.MALE) : getSurname(Gender.FEMALE);
        }
    }

    public String getMailService() {
        return mailServices.get(RandomUtils.nextInt(0, mailServices.size()));
    }

    public String getComputerHardware() {
        return computerHardware.get(RandomUtils.nextInt(0, mailServices.size()));
    }

    public String getCountry() {
        return countries.get(RandomUtils.nextInt(0, countries.size()));
    }

    public String getCity() {
        return cities.get(RandomUtils.nextInt(0, cities.size()));
    }

    public String getStreet() {
        return streets.get(RandomUtils.nextInt(0, streets.size()));
    }

    private List<String> loadVocabulary(final String fileName) {
        final String path = "src/main/resources/vocabulary/";
        final String extension = ".txt";
        final File file = new File(path + fileName + extension);
        List<String> vocabulary;
        try {
            vocabulary = FileUtils.readLines(file, StandardCharsets.UTF_8);
            log.info(String.format("Vocabulary %1$s has been loaded! %1$s size is %2$s.", fileName, vocabulary.size()));
            return vocabulary;
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }
}