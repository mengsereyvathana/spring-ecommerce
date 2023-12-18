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
import com.spring.ecommerce.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        productDto.setImage1(product.getImage1());
        productDto.setImage2(product.getImage2());
        productDto.setImage3(product.getImage3());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setId(product.getId());

        ProductDetailDto productDetailDto = new ProductDetailDto();
        productDetailDto.setId(product.getDetail().getId());
        productDetailDto.setColor(product.getDetail().getColor());
        productDetailDto.setSize(product.getDetail().getSize());

        Category categoryDto = new Category();
        categoryDto.setId(product.getCategory().getId());
        categoryDto.setName(product.getCategory().getName());
        categoryDto.setDescription(product.getCategory().getDescription());
        categoryDto.setImage(product.getCategory().getImage());

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

    public Product createProduct(ProductDto productDto, Category category, MultipartFile[] files) throws IOException {
        String uploadDir = "./images/products";

        ProductDetail productDetail = new ProductDetail();
        productDetail.setColor(productDto.getDetail().getColor());
        productDetail.setSize(productDto.getDetail().getSize());

        Product product = new Product();

        product.setName(productDto.getName());
        product.setCategory(category);
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setDescription(productDto.getDescription());

        product.setDetail(productDetailRepository.save(productDetail));

        List<String> fileNames = FileUploadUtil.saveAllFiles(uploadDir, files, true);

        product.setImage1(fileNames.get(0));
        product.setImage2(fileNames.get(1));
        product.setImage3(fileNames.get(2));

        return productRepository.save(product);
    }

    public Product updateProduct(ProductDto productDto, Long productId, MultipartFile[] files) throws Exception {
        String uploadDir = "./images/products";
        Optional<Product> optionalProduct  = productRepository.findById(productId);

        // check if product exists
        if (optionalProduct.isEmpty()) {
            throw new Exception("Product is not exist.");
        }

        Product existingProduct  = optionalProduct.get();

        Category category = categoryService.getCategoryById(productDto.getCategoryId());
        existingProduct.setCategory(category);

        existingProduct.setName(productDto.getName());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setQuantity(productDto.getQuantity());
        existingProduct.setDescription(productDto.getDescription());

        FileUploadUtil.deleteFile(uploadDir, existingProduct.getImage1());
        FileUploadUtil.deleteFile(uploadDir, existingProduct.getImage2());
        FileUploadUtil.deleteFile(uploadDir, existingProduct.getImage3());
        List<String> fileNames = FileUploadUtil.saveAllFiles(uploadDir, files, true);

        existingProduct.setImage1(fileNames.get(0));
        existingProduct.setImage2(fileNames.get(1));
        existingProduct.setImage3(fileNames.get(2));

        return productRepository.save(existingProduct);
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
