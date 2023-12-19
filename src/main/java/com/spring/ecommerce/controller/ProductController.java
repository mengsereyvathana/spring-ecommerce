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
    public ResponseEntity<ApiResponse<List<ProductDto>>> getAllProducts() {
        return new ResponseEntity<>(new ApiResponse<>(true, "product items", productService.getAllProducts()), HttpStatus.OK);
    }

    @PostMapping("/create/{categoryId}")
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(@PathVariable("categoryId") Long categoryId, @ModelAttribute ProductDto productDto, @RequestParam("imageFiles") MultipartFile[] files) throws IOException {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse<>(false, "Category does not exist", null), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse<>(true, "Product is added", productService.createProduct(productDto, optionalCategory.get(), files)), HttpStatus.CREATED);
    }

    @PutMapping("/update/{productId}/{categoryId}")
    public ResponseEntity<ApiResponse<ProductDto>> updateProduct(@PathVariable("productId") Long productId, @PathVariable("categoryId") Long categoryId, @ModelAttribute ProductDto productDto, @RequestParam("imageFiles") MultipartFile[] files) throws Exception {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse<>(false, "Category does not exist", null), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse<>(true, "Product is updated", productService.updateProduct(productDto, productId, optionalCategory.get(), files)), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable("id") Long productId) throws Exception {
        return new ResponseEntity<>(new ApiResponse<>(true, "Product is deleted", productService.deleteProduct(productId)), HttpStatus.OK);
    }
}




