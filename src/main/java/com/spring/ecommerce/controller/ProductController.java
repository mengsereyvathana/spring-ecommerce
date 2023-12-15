package com.spring.ecommerce.controller;

import com.spring.ecommerce.common.ApiResponse;
import com.spring.ecommerce.dto.ProductDto;
import com.spring.ecommerce.entity.Category;
import com.spring.ecommerce.entity.Product;
import com.spring.ecommerce.repository.CategoryRepository;
import com.spring.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Product>> createProduct(@ModelAttribute ProductDto productDto, @RequestParam("imageFiles") MultipartFile[] files) throws IOException {
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (optionalCategory.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse<>(false, "Category does not exists", null), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse<>(true, "Product has been added", productService.createProduct(productDto, optionalCategory.get(), files)), HttpStatus.CREATED);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable("productId") Long productId, @ModelAttribute ProductDto productDto, @RequestParam("imageFiles") MultipartFile[] files) throws Exception {
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (optionalCategory.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse<>(false, "Category does not exists", null), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new ApiResponse<>(true, "Product has been updated", productService.updateProduct(productDto, productId, files)), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponse<Product>> deleteProduct(@PathVariable("productId") Long productId) throws Exception {
        return new ResponseEntity<>(new ApiResponse<>(true, "Product has been updated", productService.deleteProduct(productId)), HttpStatus.OK);
    }
}




