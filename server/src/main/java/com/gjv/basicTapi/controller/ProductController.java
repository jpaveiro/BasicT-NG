package com.gjv.basicTapi.controller;

import com.gjv.basicTapi.dto.DeleteProductRequestDto;
import com.gjv.basicTapi.dto.GetProductRequestDto;
import com.gjv.basicTapi.dto.SetProductRequestDto;
import com.gjv.basicTapi.usecase.ProductService;
import com.gjv.basicTapi.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product/")
public class ProductController {
    private final ProductService productService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductService productService)
    {
        this.productService = productService;
    }

    @PostMapping("/v1/set")
    public ResponseEntity<?> setProduct(@RequestBody SetProductRequestDto request)
    {
        return Utils.executeAndLogElapsedTime(() -> productService.setProduct(request), LOGGER);
    }

    @PostMapping("/v1/get")
    public ResponseEntity<?> getProduct(@RequestBody GetProductRequestDto request)
    {
        return Utils.executeAndLogElapsedTime(() -> productService.getProduct(request), LOGGER);
    }

    @DeleteMapping("/v1/delete")
    public ResponseEntity<?> deleteProduct(@RequestBody DeleteProductRequestDto request)
    {
        return Utils.executeAndLogElapsedTime(() -> productService.deleteProduct(request), LOGGER);
    }

    @GetMapping("v1/getAll")
    public ResponseEntity<?> getAll(@RequestParam("page") int page) {
        if (page <= 0) {
            return null;
        }
        return Utils.executeAndLogElapsedTime(() -> productService.getAll(page), LOGGER);
    }
}
