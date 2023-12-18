package com.spring.ecommerce.controller;

import com.spring.ecommerce.common.ApiResponse;
import com.spring.ecommerce.entity.Category;
import com.spring.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> findAll() {
        return new ResponseEntity<>(new ApiResponse<>(true, "category items", categoryService.findAll()), HttpStatus.CREATED);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Category>> create(@ModelAttribute Category category) {
        return new ResponseEntity<>(new ApiResponse<>(true, "a new category created", categoryService.createCategory(category)), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Category>> edit(@PathVariable("id") Long id, @ModelAttribute Category category) throws IOException {
        if (!categoryService.findById(id)) {
            return new ResponseEntity<>(new ApiResponse<>(false, "category does not exists", null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ApiResponse<>(true, "category has been updated", categoryService.editCategory(id,category)), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCategory(@PathVariable("id") Long id) throws Exception {
        if(!categoryService.findById(id)) {
            return new ResponseEntity<>(new ApiResponse<>(false, "category does not exists", null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ApiResponse<>(true, "category has been updated", categoryService.deleteCategory(id)), HttpStatus.OK);
    }
}
