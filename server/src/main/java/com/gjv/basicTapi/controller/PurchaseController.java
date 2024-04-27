package com.gjv.basicTapi.controller;

import com.gjv.basicTapi.usecase.PurchaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/purchase/")
public class PurchaseController {
  @Autowired
  private PurchaseService purchaseService;

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

  @GetMapping("/v1/get")
  public ResponseEntity<?> getAll(
      @RequestParam("page") int page
  ) {
    if (page <= 0) {
      return null;
    }
    long startTime = System.currentTimeMillis();
    ResponseEntity<?> response = purchaseService.getAll(page);
    long endTime = System.currentTimeMillis();
    long elapsedTime = endTime - startTime;
    LOGGER.info("Elapsed time: " + elapsedTime + " milisseconds.");
    return response;
  }
}
