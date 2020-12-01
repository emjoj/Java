package cz.muni.fi.pv168.jate.hotelreservationsystem.data;

import cz.muni.fi.pv168.jate.hotelreservationsystem.model.Person;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static java.time.temporal.ChronoUnit.DAYS;

public class PersonGenerator {

    private static final List<String> MALE_FIRST_NAMES = List.of(
            "Adam", "Benjamin", "Cody", "David", "Edward", "Franklin", "George", "Hugh", "Isaac",
            "Jeremy", "Kyle", "Louis", "Matthew", "Nick", "Oscar", "Paul", "Richard",
            "Stanley", "Tyler", "Ulysses", "Vincent", "William", "Xavier", "Yuri", "Zachary"
    );

    private static final List<String> FEMALE_FIRST_NAMES = List.of(
            "Ashley", "Beverly", "Cassandra", "Debbie", "Elizabeth", "Felicity", "Grace", "Hannah", "Isabella",
            "Jessica", "Kelsy", "Linda", "Michelle", "Natalie", "Olivia", "Patrice", "Rachel",
            "Sophie", "Teresa", "Ursula", "Valerie", "Wendy", "Xena", "Yvonne", "Zoey"
    );

    private static final List<String> LAST_NAMES = List.of(
            "Adams", "Brown", "Carter", "Dixon", "Evans", "Fox", "Gray", "Harris", "Ingram",
            "Jones", "Knight", "Lee", "Murphy", "Norton", "Osborne", "Peterson", "Robinson",
            "Smith", "Turner", "Upton", "Vance", "Walsh", "Yardley", "Zimmerman"
    );

    private static final LocalDate MIN_BIRTH_DATE = LocalDate.now().minusYears(80);
    private static final LocalDate MAX_BIRTH_DATE = LocalDate.now().minusYears(18);

    private final Random random = new Random(133742036069L);

    public Person getRandomPerson() {
        String firstName = random.nextBoolean() ?
                selectRandomString(FEMALE_FIRST_NAMES) :
                selectRandomString(MALE_FIRST_NAMES);
        String lastName = selectRandomString(LAST_NAMES);
        LocalDate birthDate = selectRandomLocalDate(MIN_BIRTH_DATE, MAX_BIRTH_DATE);
        String evidenceID = String.format("%c%c%s",
                (char) (random.nextInt(26) + 'A'),
                (char) (random.nextInt(26) + 'A'),
                random.ints(100000, 999999).toString());
        String phoneNumber = "+421" + random.ints(100000000, 999999999);
        String email = "random@email.com";
        return new Person(firstName, lastName, birthDate, evidenceID, email, phoneNumber);
    }

    private String selectRandomString(List<String> data) {
        int index = random.nextInt(data.size());
        return data.get(index);
    }

    private LocalDate selectRandomLocalDate(LocalDate min, LocalDate max) {
        int maxDays = Math.toIntExact(DAYS.between(min, max) + 1);
        int days = random.nextInt(maxDays);
        return min.plusDays(days);
    }
}
