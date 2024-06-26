package com.gjv.basicTapi.controller;

import com.gjv.basicTapi.dto.DeleteProductRequestDto;
import com.gjv.basicTapi.dto.GetProductRequestDto;
import com.gjv.basicTapi.dto.SetProductRequestDto;
import com.gjv.basicTapi.usecase.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product/")
public class ProductController {

    @Autowired
    private ProductService productService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @PostMapping("/v1/set")
    public ResponseEntity<?> setProduct(@RequestBody SetProductRequestDto request)
    {
        long startTime = System.currentTimeMillis();
        ResponseEntity<?> response = productService.setProduct(request);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        LOGGER.info("Elapsed time: " + elapsedTime + " milisseconds.");
        return response;
    }

    @PostMapping("/v1/get")
    public ResponseEntity<?> getProduct(@RequestBody GetProductRequestDto request)
    {
        long startTime = System.currentTimeMillis();
        ResponseEntity<?> response = productService.getProduct(request);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        LOGGER.info("Elapsed time: " + elapsedTime + " milisseconds.");
        return response;
    }
    @DeleteMapping("/v1/delete")
    public ResponseEntity<?> deleteProduct(@RequestBody DeleteProductRequestDto request)
    {
        long startTime = System.currentTimeMillis();
        ResponseEntity<?> response = productService.deleteProduct(request);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        LOGGER.info("Elapsed time: " + elapsedTime + " milisseconds.");
        return response;
    }
    @GetMapping("v1/getAll")
    public ResponseEntity<?> getAll(@RequestParam("page") int page) {
        if (page <= 0) {
            return null;
        }
        long startTime = System.currentTimeMillis();
        ResponseEntity<?> response = productService.getAll(page);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        LOGGER.info("Elapsed time: " + elapsedTime + " milisseconds.");
        return response;
    }
}
