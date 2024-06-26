package com.gjv.basicTapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetProductRequestDto {
    private String idProduct;
    private String name;
    private Double price;
}
