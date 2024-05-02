package com.gjv.basicTapi.controller;

import com.gjv.basicTapi.dto.NewPurchaseRequestDto;
import com.gjv.basicTapi.usecase.PurchaseService;
import com.gjv.basicTapi.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/purchase/")
public class PurchaseController {
  @Autowired
  private PurchaseService purchaseService;

  private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseController.class);

  @GetMapping("/v1/get")
  public ResponseEntity<?> getAll(@RequestParam("page") int page) {
    if (page <= 0) {
      return null;
    }
    return Utils.executeAndLogElapsedTime(() -> purchaseService.getAll(page), LOGGER);
  }

  @PostMapping("/v1/sell")
  public ResponseEntity<?> sell(@RequestBody NewPurchaseRequestDto purchaseInfo) {
    return Utils.executeAndLogElapsedTime(() -> purchaseService.sellProduct(purchaseInfo), LOGGER);
  }
}
