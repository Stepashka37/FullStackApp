package ru.dimax.mapper;

import org.mapstruct.*;
import org.springframework.security.core.GrantedAuthority;
import ru.dimax.dto.CustomerRegistrationRequest;
import ru.dimax.dto.CustomerResponse;
import ru.dimax.dto.CustomerUpdateRequest;
import ru.dimax.model.Customer;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "roles", expression = "java(getDefaultRoles())")
    CustomerResponse toDto(Customer customer);


    @Mapping(target = "id", ignore = true)
    Customer toModel(CustomerRegistrationRequest customerRegistrationRequest);

    @Mapping(source = "name", target = "name", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "email", target = "email", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "age", target = "age", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Customer toModelForUpdate(CustomerUpdateRequest request, @MappingTarget Customer customer);

    default List<String> getDefaultRoles() {
        return List.of("ROLE_USER");
    }
}
