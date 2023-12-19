package com.spring.ecommerce.mapper;

import com.spring.ecommerce.dto.CategoryDto;
import com.spring.ecommerce.dto.ProductDto;
import com.spring.ecommerce.entity.Product;

public class ProductMapper {
    public static ProductDto mapToProductDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        productDto.setQuantity(product.getQuantity());
        productDto.setImage1(product.getImage1());
        productDto.setImage2(product.getImage2());
        productDto.setImage3(product.getImage3());
        productDto.setDetail(product.getDetail());
        productDto.setCategory(product.getCategory());
        return productDto;
    }
    public static Product mapToProduct(ProductDto productDto){
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setQuantity(productDto.getQuantity());
        product.setImage1(productDto.getImage1());
        product.setImage2(productDto.getImage2());
        product.setImage3(productDto.getImage3());
        product.setDetail(productDto.getDetail());
        product.setCategory(productDto.getCategory());
        return product;
    }
}
