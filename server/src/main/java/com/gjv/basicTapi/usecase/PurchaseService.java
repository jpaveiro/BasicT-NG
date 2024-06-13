package com.gjv.basicTapi.usecase;

import com.gjv.basicTapi.dto.NewPurchaseRequestDto;
import com.gjv.basicTapi.model.Purchase;
import com.gjv.basicTapi.repository.PurchaseRepository;
import com.gjv.basicTapi.utils.Utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {
  private final PurchaseRepository purchaseRepository;

  public PurchaseService(PurchaseRepository purchaseRepository) {
    this.purchaseRepository = purchaseRepository;
  }

  /**
   * Retorna uma lista paginada de todas as compras registradas no sistema.
   *
   * @param page O número da página a ser recuperada.
   * @return ResponseEntity contendo a lista paginada de compras ou uma resposta de erro se nenhuma compra for encontrada.
   */
  public ResponseEntity<?> getAll(int page)
  {
    Page<Purchase> purchasePage = purchaseRepository.findAll(
        PageRequest.of(page - 1, 7, Sort.by(Sort.Direction.DESC, "purchaseDate"))
    );

    if (purchasePage.isEmpty()) {
      return Utils.generateStandardResponseEntity("Error: No purchase found", HttpStatus.NOT_FOUND);
    }

    return ResponseEntity.status(HttpStatus.ACCEPTED).body(purchasePage);
  }

  /**
   * Registra a venda de um produto no sistema com base nas informações fornecidas.
   *
   * @param purchaseInfo Um objeto NewPurchaseRequestDto contendo as informações da nova venda.
   * @return ResponseEntity indicando se a venda do produto foi registrada com sucesso ou uma resposta de erro se ocorrer algum problema durante o processo.
   */
  public ResponseEntity<?> sellProduct(NewPurchaseRequestDto purchaseInfo)
  {
    Purchase purchase = Purchase.builder()
       .idUser(purchaseInfo.getUserId())
       .idProduct(purchaseInfo.getProductId())
       .productQuantity(purchaseInfo.getQuantity())
       .totalAmount(purchaseInfo.getTotalAmount())
       .idPurchase(Utils.generateId())
       .purchaseCode(purchaseInfo.getPurchaseCode())
       .paymentMethod(purchaseInfo.getPaymentMethod())
       .build();

    try {
      purchaseRepository.save(purchase);
      return Utils.generateStandardResponseEntity("Success: Product has been sold.", HttpStatus.OK);
    } catch (Exception e) {
      return Utils.generateStandardResponseEntity("Error: Product can't be sold.", HttpStatus.BAD_REQUEST);
    }
  }
}
