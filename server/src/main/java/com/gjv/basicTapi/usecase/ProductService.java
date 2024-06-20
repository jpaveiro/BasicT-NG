package com.gjv.basicTapi.usecase;

import com.gjv.basicTapi.dto.ProductRequestDto;
import com.gjv.basicTapi.model.Product;
import com.gjv.basicTapi.model.StandardResponse;
import com.gjv.basicTapi.repository.ProductRepository;
import com.gjv.basicTapi.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Registra um novo produto no sistema com base nas informações fornecidas.
     *
     * @param request Um objeto SetProductRequestDto contendo as informações do novo produto a ser registrado.
     * @return ResponseEntity indicando se o produto foi registrado com sucesso ou uma resposta de erro se algumas das informações necessárias não for fornecida ou se ocorrer algum problema durante o registro.
     */
    public ResponseEntity<?> setProduct(ProductRequestDto request)
    {
        String barCode, productName;
        double price;

        try
        {
            barCode = Utils.checkBarCode(request.getIdProduct().toUpperCase());
            productName = request.getName().toUpperCase();
            price = request.getPrice();
        }
        catch (NullPointerException e)
        {
            return Utils.generateStandardResponseEntity("Error: You must fill all fields.", HttpStatus.BAD_REQUEST);
        }

        Map<String, String> fieldsToValidate = new HashMap<>();
        fieldsToValidate.put("barCode", barCode);
        fieldsToValidate.put("productName", productName);
        fieldsToValidate.put("price", Objects.toString(price));

        for (Map.Entry<String, String> entry : fieldsToValidate.entrySet()) {
            ResponseEntity<StandardResponse> responseError = Utils.validateField(entry.getKey(), entry.getValue());
            if (responseError != null) {
                return responseError;
            }
        }

        try
        {
            productRepository.setProduct(
                    barCode,
                    productName,
                    price);

            return Utils.generateStandardResponseEntity("Success: Product registered.", HttpStatus.OK);
        }
        catch (Exception e)
        {
            LOGGER.info("Error: The system was unable to register the product.\nDetails: {}", e.getMessage());
            return Utils.generateStandardResponseEntity("Error: The system was unable to register the product. Probably product already registered.", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Retorna as informações de um produto com base no ID fornecido.
     *
     * @param request Um objeto GetProductRequestDto contendo o ID do produto a ser recuperado.
     * @return ResponseEntity contendo as informações do produto solicitado ou uma resposta de erro se o ID do produto não for fornecido ou se o produto não for encontrado.
     */
    public ResponseEntity<?> getProduct(ProductRequestDto request)
    {
        if (request.getIdProduct() == null) {
            return Utils.generateStandardResponseEntity("Error: You must fill in all fields.", HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<?> deleteProduct(ProductRequestDto request)
    {
        String id = request.getIdProduct();
        if (request.getIdProduct() == null) {
            return Utils.generateStandardResponseEntity("Error: You must fill in all fields.", HttpStatus.BAD_REQUEST);
        }

        Product product = productRepository.getProduct(request.getIdProduct());

        ResponseEntity<?> responseError = Utils.validateField("product", product);
        if (responseError != null) {
            return responseError;
        }

        productRepository.deleteProduct(id);

        return Utils.generateStandardResponseEntity("Success: Product has been deleted.", HttpStatus.OK);
    }

    /**
     * Retorna uma lista paginada de todos os produtos registrados no sistema.
     *
     * @param page O número da página a ser recuperada.
     * @return ResponseEntity contendo a lista paginada de produtos ou uma resposta de erro se nenhum produto for encontrado.
     */
    public ResponseEntity<?> getAll(int page)
    {
        Page<Product> productPage = productRepository.findAll(
                PageRequest.of(page - 1, 7, Sort.by(Sort.Direction.DESC, "createdAt")));

        if (productPage.isEmpty()) {
            return Utils.generateStandardResponseEntity("Error: no products found", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(productPage);
    }
}
