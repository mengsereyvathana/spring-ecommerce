package com.spring.ecommerce.service;

import com.spring.ecommerce.dto.CategoryDto;
import com.spring.ecommerce.dto.ProductDetailDto;
import com.spring.ecommerce.dto.ProductDto;
import com.spring.ecommerce.entity.Category;
import com.spring.ecommerce.entity.Product;
import com.spring.ecommerce.entity.ProductDetail;
import com.spring.ecommerce.exception.ProductNotExistsException;
import com.spring.ecommerce.repository.ProductDetailRepository;
import com.spring.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductDetailRepository productDetailRepository;

    public List<ProductDto> getAllProducts() {
        List<Product> allProducts = productRepository.findAll();

        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product: allProducts) {
            productDtos.add(getProductDto(product));
        }
        return productDtos;
    }


    public ProductDto getProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setDescription(product.getDescription());
        productDto.setImageURL(product.getImageUrl());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setId(product.getId());

        ProductDetailDto productDetailDto = new ProductDetailDto();
        productDetailDto.setId(product.getDetail().getId());
        productDetailDto.setColor(product.getDetail().getColor());
        productDetailDto.setSize(product.getDetail().getSize());

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(product.getCategory().getId());
        categoryDto.setName(product.getCategory().getName());
        categoryDto.setDescription(product.getCategory().getDescription());
        categoryDto.setImageURL(product.getCategory().getImageUrl());

        productDto.setDetail(productDetailDto);
        productDto.setCategory(categoryDto);
        return productDto;
    }

    public Product findById(Long productId) throws ProductNotExistsException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotExistsException("product id is invalid: " + productId);
        }
        return optionalProduct.get();
    }

    public void createProduct(ProductDto productDto, Category category) {
        ProductDetail productDetail = new ProductDetail();
        productDetail.setColor(productDto.getDetail().getColor());
        productDetail.setSize(productDto.getDetail().getSize());

        Product product = new Product();
        product.setDescription(productDto.getDescription());
        product.setImageUrl(productDto.getImageURL());
        product.setName(productDto.getName());
        product.setCategory(category);
        product.setPrice(productDto.getPrice());
        product.setDetail(productDetailRepository.save(productDetail));

        productRepository.save(product);
    }

    public void updateProduct(ProductDto productDto, Long productId) throws Exception {
        Optional<Product> optionalProduct  = productRepository.findById(productId);

        // check if product does not exists
        if (optionalProduct.isEmpty()) {
            throw new Exception("product is not present");
        }

        Product existingProduct  = optionalProduct.get();

        Category category = categoryService.getCategoryById(productDto.getCategoryId());
        existingProduct.setCategory(category);

        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setImageUrl(productDto.getImageURL());
        existingProduct.setName(productDto.getName());
        existingProduct.setPrice(productDto.getPrice());
        productRepository.save(existingProduct);
    }
}
