package com.spring.ecommerce.service;

import com.spring.ecommerce.dto.ProductDetailDto;
import com.spring.ecommerce.dto.ProductDto;
import com.spring.ecommerce.entity.Category;
import com.spring.ecommerce.entity.Product;
import com.spring.ecommerce.entity.ProductDetail;
import com.spring.ecommerce.exception.ProductNotExistsException;
import com.spring.ecommerce.mapper.CategoryMapper;
import com.spring.ecommerce.mapper.ProductMapper;
import com.spring.ecommerce.repository.ProductDetailRepository;
import com.spring.ecommerce.repository.ProductRepository;
import com.spring.ecommerce.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductDetailRepository productDetailRepository;

    public List<ProductDto> getAllProducts() {
        List<Product> categories = productRepository.findAll();
        return categories.stream().map(ProductMapper::mapToProductDto).collect(Collectors.toList());
    }


//    public ProductDto getProductDto(Product product) {
//        ProductDto productDto = new ProductDto();
//        productDto.setDescription(product.getDescription());
//        productDto.setQuantity(product.getQuantity());
//        productDto.setImage1(product.getImage1());
//        productDto.setImage2(product.getImage2());
//        productDto.setImage3(product.getImage3());
//        productDto.setName(product.getName());
//        productDto.setPrice(product.getPrice());
//        productDto.setId(product.getId());
//
//        ProductDetailDto productDetailDto = new ProductDetailDto();
//        productDetailDto.setId(product.getDetail().getId());
//        productDetailDto.setColor(product.getDetail().getColor());
//        productDetailDto.setSize(product.getDetail().getSize());
//
//        Category category = new Category();
//        category.setId(product.getCategory().getId());
//        category.setName(product.getCategory().getName());
//        category.setDescription(product.getCategory().getDescription());
//        category.setImage(product.getCategory().getImage());
//
//        productDto.setDetail(productDetailDto);
//        productDto.setCategory(category);
//        return productDto;
//    }

    public Product findById(Long productId) throws ProductNotExistsException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotExistsException("product id is invalid: " + productId);
        }
        return optionalProduct.get();
    }

    public ProductDto createProduct(ProductDto productDto, Category category, MultipartFile[] files) throws IOException {
        String uploadDir = "./images/products";

        ProductDetail productDetail = new ProductDetail();
        productDetail.setColor(productDto.getDetail().getColor());
        productDetail.setSize(productDto.getDetail().getSize());

        Product product = ProductMapper.mapToProduct(productDto);

        List<String> fileNames = FileUploadUtil.saveAllFiles(uploadDir, files, true);

        product.setImage1(fileNames.get(0));
        product.setImage2(fileNames.get(1));
        product.setImage3(fileNames.get(2));

        product.setDetail(productDetailRepository.save(productDetail));
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);
        return ProductMapper.mapToProductDto(savedProduct);

//        return productRepository.save(product);
    }

    public ProductDto updateProduct(ProductDto productDto, Long productId, Category category,MultipartFile[] files) throws Exception {
        String uploadDir = "./images/products";
        Optional<Product> optionalProduct  = productRepository.findById(productId);

        // check if product exists
        if (optionalProduct.isEmpty()) {
            throw new Exception("Product is not exist.");
        }

        Product existingProduct  = optionalProduct.get();

        existingProduct.setName(productDto.getName());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setQuantity(productDto.getQuantity());
        existingProduct.setDescription(productDto.getDescription());

        FileUploadUtil.deleteFile(uploadDir, existingProduct.getImage1());
        FileUploadUtil.deleteFile(uploadDir, existingProduct.getImage2());
        FileUploadUtil.deleteFile(uploadDir, existingProduct.getImage3());

        existingProduct.setCategory(category);

        List<String> fileNames = FileUploadUtil.saveAllFiles(uploadDir, files, true);

        existingProduct.setImage1(fileNames.get(0));
        existingProduct.setImage2(fileNames.get(1));
        existingProduct.setImage3(fileNames.get(2));

        Product updatedProduct = productRepository.save(existingProduct);
        return ProductMapper.mapToProductDto(updatedProduct);
    }

    public String deleteProduct(Long productId) throws Exception {
        String uploadDir = "./images/products";
        Optional<Product> optionalProduct  = productRepository.findById(productId);

        if (optionalProduct.isEmpty()) {
            throw new Exception("Product is not exist.");
        }

        Product existingProduct  = optionalProduct.get();

        FileUploadUtil.deleteFile(uploadDir, existingProduct.getImage1());
        FileUploadUtil.deleteFile(uploadDir, existingProduct.getImage2());
        FileUploadUtil.deleteFile(uploadDir, existingProduct.getImage3());
        productRepository.deleteById(productId);
        return "Product successfully deleted" + productId;
    }

    public ProductDto getById(Long id) throws IOException {
        Product product = productRepository.findById(id).orElse(null);
        if(product == null) throw new IOException("No product is found");

        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        productDto.setQuantity(product.getQuantity());
        productDto.setCategory(product.getCategory());
        productDto.setImage1(product.getImage1());
        productDto.setImage2(product.getImage2());
        productDto.setImage3(product.getImage3());
        return productDto;
    }
}
