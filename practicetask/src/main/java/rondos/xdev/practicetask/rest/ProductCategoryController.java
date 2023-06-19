package rondos.xdev.practicetask.rest;

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
    public List<ProductCategory> getAllProductCategories() {
        return productCategoryService.getAllProductCategories();
    }

    @PostMapping
    public ProductCategory createProductCategory(@RequestParam String name) {
        return productCategoryService.createProductCategory(name);
    }

    @PutMapping
    public ProductCategory updateProductCategory(@RequestParam String id, String name) {
        return productCategoryService.updateProductCategory(id, name);
    }

    @DeleteMapping
    public void deleteProductCategory(@RequestParam String id) {
        productCategoryService.deleteProductCategory(id);
    }
}