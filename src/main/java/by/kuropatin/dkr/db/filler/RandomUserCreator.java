package by.kuropatin.dkr.db.filler;

import by.kuropatin.dkr.db.filler.util.FillerUtils;
import by.kuropatin.dkr.model.Gender;
import by.kuropatin.dkr.model.request.UserCreateRequest;
import com.ibm.icu.text.Transliterator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

import static by.kuropatin.dkr.db.filler.util.FillerUtils.randomInt;

@Component
@RequiredArgsConstructor
public class RandomUserCreator {

    private static final Transliterator transliterator = Transliterator.getInstance("Russian-Latin/BGN");
    private final Vocabulary vocabulary;

    public UserCreateRequest randomRequest() {
        final Gender gender = generateGender();
        final String name = vocabulary.getName(gender);
        final String surname = vocabulary.getSurname(gender);
        final String birthDate = generateBirthDate();

        return UserCreateRequest.builder()
                .login(generateLoginPassword())
                .password(generateLoginPassword())
                .name(name)
                .surname(surname)
                .gender(gender)
                .birthDate(birthDate)
                .phone(generatePhone())
                .email(generateEmail(name, surname, birthDate))
                .build();
    }

    private Gender generateGender() {
        return randomInt(0, 1) == 0 ? Gender.MALE : Gender.FEMALE;
    }

    private String generateLoginPassword() {
        return RandomStringUtils.randomAlphanumeric(8, 20);
    }

    private String generateBirthDate() {
        final long minDay = LocalDate.of(1960, 1, 1).toEpochDay();
        final long maxDay = LocalDate.of(2002, 12, 31).toEpochDay();
        final long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay).format(DateTimeFormatter.ISO_DATE);
    }

    private String generatePhone() {
        return "+375" + RandomStringUtils.randomNumeric(9);
    }

    private String generateEmail(final String name, final String surname, final String birthDate) {
        final StringBuilder sb = new StringBuilder();
        switch (FillerUtils.randomInt(1, 3)) {
            case 1 -> sb.append(firstLetter(transliterateLowerCase(name)))
                    .append(".")
                    .append(transliterateLowerCase(surname));
            case 2 -> sb.append(transliterateLowerCase(name))
                    .append(".")
                    .append(firstLetter(transliterateLowerCase(surname)));
            default -> sb.append(transliterateLowerCase(name))
                    .append(".")
                    .append(transliterateLowerCase(surname));
        }
        switch (FillerUtils.randomInt(1, 3)) {
            case 1 -> sb.append(birthDate, 0, 4);
            case 2 -> sb.append(birthDate, 2, 4);
            default -> sb.append(FillerUtils.randomInt(1, 3));
        }
        sb.append(vocabulary.getMailService());
        return sb.toString();
    }

    private String transliterateLowerCase(final String s) {
        return transliterator.transliterate(s.toLowerCase()
                .replace("ё", "е")
                .replace("ь", "")
                .replace("ъ", "")
        );
    }

    private char firstLetter(final String s) {
        return s.charAt(0);
    }
}