package com.gjv.basicTapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/* Quando for adicionar atributos, usar CONSTANTES e não PRIMITIVAS */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private String name;
    private String cellphone;
    private String email;
    private String cpf;
    private String rg;
    private String password;
}
