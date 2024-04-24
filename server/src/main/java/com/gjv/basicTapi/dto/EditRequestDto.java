package com.gjv.basicTapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditRequestDto {
    private String id;
    private String password;
    private String name;
    private String cellphone;
    private String email;
    private String cpf;
    private String rg;
}
