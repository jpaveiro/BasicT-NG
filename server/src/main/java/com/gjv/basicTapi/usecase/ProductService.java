package com.gjv.basicTapi.usecase;

import com.gjv.basicTapi.dto.DeleteProductRequestDto;
import com.gjv.basicTapi.dto.GetProductRequestDto;
import com.gjv.basicTapi.dto.SetProductRequestDto;
import com.gjv.basicTapi.model.Product;
import com.gjv.basicTapi.model.StandardResponse;
import com.gjv.basicTapi.model.User;
import com.gjv.basicTapi.repository.ProductRepository;
import com.gjv.basicTapi.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    /**
     * Registra um novo produto no sistema com base nas informações fornecidas.
     *
     * @param request Um objeto SetProductRequestDto contendo as informações do novo produto a ser registrado.
     * @return ResponseEntity indicando se o produto foi registrado com sucesso ou uma resposta de erro se algumas das informações necessárias não for fornecida ou se ocorrer algum problema durante o registro.
     */
    public ResponseEntity<?> setProduct(SetProductRequestDto request) {
        if (request.getIdProduct() == null || request.getName() == null
                || request.getPrice() == null) {
            StandardResponse response = StandardResponse.builder()
                    .message("Error: You must fill in all fields.")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        String barCode = Utils.checkBarCode(request.getIdProduct());
        String productName = request.getName();
        double price = request.getPrice();
        barCode.toUpperCase();
        productName.toUpperCase();

        ResponseEntity<?> responseBarCode = Utils.validateField("idProduct", barCode);
        if (responseBarCode != null) {
            return responseBarCode;
        }

        try {
            productRepository.setProduct(
                    barCode,
                    productName,
                    price);

            StandardResponse response = StandardResponse.builder()
                    .message("Success: Product registered.")
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            StandardResponse response = StandardResponse.builder()
                    .message(
                            "Error: The system was unable to register the product. Probably product already registered.")
                    .build();
            LOGGER.info("Error: The system was unable to register the product.\nDetails: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    /**
     * Retorna as informações de um produto com base no ID fornecido.
     *
     * @param request Um objeto GetProductRequestDto contendo o ID do produto a ser recuperado.
     * @return ResponseEntity contendo as informações do produto solicitado ou uma resposta de erro se o ID do produto não for fornecido ou se o produto não for encontrado.
     */
    public ResponseEntity<?> getProduct(GetProductRequestDto request) {
        if (request.getIdProduct() == null) {
            StandardResponse response = StandardResponse.builder()
                    .message("Error: You must fill in all fields.")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Product product = productRepository.getProduct(request.getIdProduct());

        ResponseEntity<?> responseError = Utils.validateField("product", product);
        if (responseError != null) {
            return responseError;
        }

        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    /**
     * Remove um produto do sistema com base no ID fornecido.
     *
     * @param request Um objeto DeleteProductRequestDto contendo o ID do produto a ser removido.
     * @return ResponseEntity indicando que o produto foi removido com sucesso ou uma resposta de erro se o ID do produto não for fornecido.
     */
    public ResponseEntity<?> deleteProduct(DeleteProductRequestDto request) {
        String id = request.getIdProduct();
        if (request.getIdProduct() == null) {
            StandardResponse response = StandardResponse.builder()
                    .message("Error: id not provided.")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        Product product = productRepository.getProduct(request.getIdProduct());

        productRepository.deleteProduct(id);

        StandardResponse response = StandardResponse.builder()
                .message("Success: Product has been deleted.")
                .build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    /**
     * Retorna uma lista paginada de todos os produtos registrados no sistema.
     *
     * @param page O número da página a ser recuperada.
     * @return ResponseEntity contendo a lista paginada de produtos ou uma resposta de erro se nenhum produto for encontrado.
     */
    public ResponseEntity<?> getAll(int page) {
        Page<Product> productPage = productRepository.findAll(
                PageRequest.of(page - 1, 7, Sort.by(Sort.Direction.DESC, "createdAt")));

        if (productPage.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: no products found");
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(productPage);
    }
}
