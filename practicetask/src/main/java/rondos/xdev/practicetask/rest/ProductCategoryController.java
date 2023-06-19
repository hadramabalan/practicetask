package rondos.xdev.practicetask.rest;

import org.springframework.web.bind.annotation.*;
import rondos.xdev.practicetask.model.Customer;
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
    public void createProductCategory(@RequestParam String name){
        productCategoryService.createProductCategory(name);
    }
    @PutMapping
    public void updateProductCategory(@RequestParam String id, String name){
        productCategoryService.updateProductCategory(id, name);
    }

    @DeleteMapping
    public void deleteProductCategory(@RequestParam String id){
        productCategoryService.deleteProductCategory(id);
    }
}