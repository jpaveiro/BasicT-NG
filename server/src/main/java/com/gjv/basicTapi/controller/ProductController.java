package com.gjv.basicTapi.controller;

import com.gjv.basicTapi.dto.AddProductRequestDto;
import com.gjv.basicTapi.model.Product;
import com.gjv.basicTapi.model.User;
import com.gjv.basicTapi.usecase.ProductService;
import com.gjv.basicTapi.usecase.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @PostMapping("/v1/set")
    public ResponseEntity<?> setProduct(@RequestBody AddProductRequestDto request)
    {
        long startTime = System.currentTimeMillis();
        ResponseEntity<?> response = productService.setProduct(request);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        LOGGER.info("Elapsed time: " + elapsedTime + " milisseconds.");
        return response;
    }
}
