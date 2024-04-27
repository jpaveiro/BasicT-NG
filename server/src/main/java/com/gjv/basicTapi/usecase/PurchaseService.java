package com.gjv.basicTapi.usecase;

import com.gjv.basicTapi.model.Purchase;
import com.gjv.basicTapi.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PurchaseService {
  @Autowired
  private PurchaseRepository purchaseRepository;

  public ResponseEntity<?> getAll(int page) {
    Page<Purchase> purchasePage = purchaseRepository.findAll(
        PageRequest.of(page - 1, 7, Sort.by(Sort.Direction.DESC, "createdAt"))
    );

    if (purchasePage.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: no products found");
    }

    return ResponseEntity.status(HttpStatus.ACCEPTED).body(purchasePage);
  }
}
