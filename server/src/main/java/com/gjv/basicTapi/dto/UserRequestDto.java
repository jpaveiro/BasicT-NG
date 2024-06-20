package com.gjv.basicTapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private String idUser;
    private String name;
    private String cellphone;
    private String email;
    private String cpf;
    private String rg;
    private String stateRg;
    private String birthDate;
    private String password;

    private String emailAdmin;
    private String passwordAdmin;
}
