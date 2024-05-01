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
            LOGGER.info("Error: The system was unable to register the product.\nDetails: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

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

    public ResponseEntity<?> getAll(int page) {
        Page<Product> productPage = productRepository.findAll(
                PageRequest.of(page - 1, 7, Sort.by(Sort.Direction.DESC, "createdAt")));

        if (productPage.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: no products found");
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(productPage);
    }
}
