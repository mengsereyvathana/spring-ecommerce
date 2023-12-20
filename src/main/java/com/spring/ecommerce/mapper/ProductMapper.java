package com.spring.ecommerce.mapper;

import com.spring.ecommerce.dto.CategoryDto;
import com.spring.ecommerce.dto.ProductDto;
import com.spring.ecommerce.entity.Category;
import com.spring.ecommerce.entity.Product;

import java.util.HashMap;
import java.util.Map;

public class ProductMapper {
    private static final Map<Product, ProductDto> mappedProductDto = new HashMap<>();
    private static final Map<ProductDto, Product> mappedProduct = new HashMap<>();
    public static ProductDto mapToProductDto(Product product){
        if (product == null) {
            return null;
        }

        if (mappedProductDto.containsKey(product)) {
            return mappedProductDto.get(product);
        }

        ProductDto productDto = new ProductDto();
        mappedProductDto.put(product, productDto);

        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        productDto.setQuantity(product.getQuantity());
        productDto.setImage1(product.getImage1());
        productDto.setImage2(product.getImage2());
        productDto.setImage3(product.getImage3());
        productDto.setDetail(ProductDetailMapper.mapToProductDetailDto(product.getDetail()));
        productDto.setCategory(CategoryMapper.mapToCategoryDto(product.getCategory()));

        return productDto;
    }
    public static Product mapToProduct(ProductDto productDto){
        if (productDto == null) {
            return null;
        }

        if (mappedProduct.containsKey(productDto)) {
            return mappedProduct.get(productDto);
        }

        Product product = new Product();
        mappedProduct.put(productDto, product);

        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setQuantity(productDto.getQuantity());
        product.setImage1(productDto.getImage1());
        product.setImage2(productDto.getImage2());
        product.setImage3(productDto.getImage3());
        product.setDetail(ProductDetailMapper.mapToProductDetail(productDto.getDetail()));
        product.setCategory(CategoryMapper.mapToCategory(productDto.getCategory()));
        return product;
    }

    public static ProductDto mapToProductDtoWithoutCategory(Product product) {
        ProductDto productDto = mapToProductDto(product);
        productDto.setCategory(null);
        return productDto;
    }

}
