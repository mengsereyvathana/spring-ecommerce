package com.spring.ecommerce.mapper;

import com.spring.ecommerce.dto.CartDto;
import com.spring.ecommerce.dto.ProductDetailDto;
import com.spring.ecommerce.entity.Cart;
import com.spring.ecommerce.entity.Product;
import com.spring.ecommerce.entity.ProductDetail;

import java.util.stream.Collectors;

public class ProductDetailMapper {
    public static ProductDetailDto mapToProductDetailDto(ProductDetail detail){
        ProductDetailDto productDetailDto = new ProductDetailDto();
        productDetailDto.setId(detail.getId());
        productDetailDto.setSize(detail.getSize());
        productDetailDto.setColor(detail.getColor());
        return productDetailDto;
    }
    public static ProductDetail mapToProductDetail(ProductDetailDto productDetailDto){
        ProductDetail detail = new ProductDetail();
        detail.setId(productDetailDto.getId());
        detail.setSize(productDetailDto.getSize());
        detail.setColor(productDetailDto.getColor());
        return detail;
    }
}
