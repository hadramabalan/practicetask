package rondos.xdev.practicetask.rest;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rondos.xdev.practicetask.model.ProductCategory;
import rondos.xdev.practicetask.service.ProductCategoryService;

import java.util.List;

@RestController
@RequestMapping("/productcategories")
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @Operation(summary = "Get all product categories", description = "Get the details of all existing product categories.")
    public List<ProductCategory> getAllProductCategories() {
        return productCategoryService.getAllProductCategories();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create product category", description = "Create a product category with a given name.")
    public ProductCategory createProductCategory(@RequestParam String name) {
        return productCategoryService.createProductCategory(name);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update product category by ID", description = "Update product category name by their ID.")
    public ProductCategory updateProductCategory(@RequestParam String id, String name) {
        return productCategoryService.updateProductCategory(id, name);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete product category by ID", description = "Delete a product category by their ID.")
    public void deleteProductCategory(@RequestParam String id) {
        productCategoryService.deleteProductCategory(id);
    }
}