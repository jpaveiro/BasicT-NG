package com.gjv.basicTapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteUserRequestDto {
    private String idUser;
    private String emailAdmin;
    private String passwordAdmin;
}
