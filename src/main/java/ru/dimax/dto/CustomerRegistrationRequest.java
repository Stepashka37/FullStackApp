package ru.dimax.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dimax.enums.Gender;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerRegistrationRequest {

    private String name;
    private String email;
    private String password;
    private Integer age;
    private Gender gender;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerRegistrationRequest request = (CustomerRegistrationRequest) o;
        return Objects.equals(name, request.name) && Objects.equals(email, request.email) && Objects.equals(age, request.age) && gender == request.gender && Objects.equals(password, request.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, age, gender, password);
    }
}
