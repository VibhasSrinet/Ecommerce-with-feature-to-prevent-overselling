package org.example.productcatalogservice_may2024.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class UserDto {
    private String email;

    private List<String> roles = new ArrayList<>();
}
