package com.gjv.basicTapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewPurchaseRequestDto {
  private String userId;
  private String productId;
  private int quantity;
  private double totalAmount;
}
