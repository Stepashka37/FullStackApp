package ru.dimax;

import com.github.javafaker.Faker;

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
}
