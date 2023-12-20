package com.spring.ecommerce.converter;

import com.spring.ecommerce.dtos.ProductDto;
import com.spring.ecommerce.entity.Product;
import org.springframework.core.convert.converter.Converter;

public class ProductToProductDtoConverter implements Converter<Product, ProductDto> {
    @Override
    public ProductDto convert(Product source) {
//        ProductDto productDto = new ProductDto(source.getId(), source.getName(), source.getPrice(), source.getDescription(), source.getQuantity(), source.getImage1(), source.getImage2(), source.getImage3(), source.getDetail(), source.getCategory());
        return null;
    }
}
