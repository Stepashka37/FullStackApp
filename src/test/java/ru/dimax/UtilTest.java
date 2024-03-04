package ru.dimax;

import com.github.javafaker.Faker;
import ru.dimax.enums.Gender;

public class UtilTest {

    private Faker FAKER = new Faker();

    public String randomName() {
        return FAKER.name().fullName();
    }

    public String randomEmail() {
        return FAKER.internet().emailAddress();
    }

    public Integer randomAge() {
        return FAKER.random().nextInt(100);
    }

    public Gender maleGender() {
        return Gender.MALE;
    }

    public Gender femaleGender() {
        return Gender.FEMALE;
    }

    public String getPassword() {
        return "password";
    }
}
