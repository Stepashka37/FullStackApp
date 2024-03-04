package ru.dimax.dto;

import lombok.*;
import ru.dimax.enums.Gender;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomerResponse {

    private Integer id;
    private String name;
    private String email;
    private Integer age;
    private Gender gender;
    private List<String> roles;
    private String username;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerResponse response = (CustomerResponse) o;
        return Objects.equals(id, response.id) && Objects.equals(name, response.name) && Objects.equals(email, response.email) && Objects.equals(age, response.age) && Objects.equals(gender, response.gender) && Objects.equals(roles, response.roles) && Objects.equals(username, response.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, age, gender, roles, username);
    }
}
