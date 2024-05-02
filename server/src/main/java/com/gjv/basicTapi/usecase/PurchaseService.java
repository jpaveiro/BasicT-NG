package com.gjv.basicTapi.usecase;

import com.gjv.basicTapi.dto.NewPurchaseRequestDto;
import com.gjv.basicTapi.model.Purchase;
import com.gjv.basicTapi.repository.PurchaseRepository;
import com.gjv.basicTapi.utils.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {
  @Autowired
  private PurchaseRepository purchaseRepository;

  /**
   * Retorna uma lista paginada de todas as compras registradas no sistema.
   *
   * @param page O número da página a ser recuperada.
   * @return ResponseEntity contendo a lista paginada de compras ou uma resposta de erro se nenhuma compra for encontrada.
   */
  public ResponseEntity<?> getAll(int page) {
    Page<Purchase> purchasePage = purchaseRepository.findAll(
        PageRequest.of(page - 1, 7, Sort.by(Sort.Direction.DESC, "purchaseDate"))
    );

    if (purchasePage.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: no purchase found");
    }

    return ResponseEntity.status(HttpStatus.ACCEPTED).body(purchasePage);
  }

  /**
   * Registra a venda de um produto no sistema com base nas informações fornecidas.
   *
   * @param purchaseInfo Um objeto NewPurchaseRequestDto contendo as informações da nova venda.
   * @return ResponseEntity indicando se a venda do produto foi registrada com sucesso ou uma resposta de erro se ocorrer algum problema durante o processo.
   */
  public ResponseEntity<?> sellProduct(NewPurchaseRequestDto purchaseInfo) {
    Purchase purchase = Purchase.builder()
       .idUser(purchaseInfo.getUserId())
       .idProduct(purchaseInfo.getProductId())
       .productQuantity(purchaseInfo.getQuantity())
       .totalAmount(purchaseInfo.getTotalAmount())
       .idPurchase(Utils.generateId())
       .purchaseCode(purchaseInfo.getPurchaseCode())
       .build();

    try {
      purchaseRepository.save(purchase);
      return ResponseEntity.status(HttpStatus.ACCEPTED).body("Success: Product has been sold.");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Product can't be sold.");
    }
  }
}
