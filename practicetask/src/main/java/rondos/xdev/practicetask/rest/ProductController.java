package rondos.xdev.practicetask.rest;

import org.springframework.web.bind.annotation.*;
import rondos.xdev.practicetask.model.Product;
import rondos.xdev.practicetask.service.ProductService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    //TODO GetMapping with filters once the customer one works

    @PostMapping
    //TODO make name and category mandatory
    public Product createProduct(@RequestParam String name, String category, Map<String, Object> optionals) {
        return productService.createProduct(name, category, optionals);
    }


    @PutMapping
    public Product updateProduct(@RequestParam String id, Map<String, Object> changes) {
        return productService.updateProduct(id, changes);
    }

    @DeleteMapping
    public void deleteProduct(@RequestParam String id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/customers")
    public List<Product> getProductsByCustomerLastNameStartsWith(@RequestParam("lastNamePrefix") String lastNamePrefix) {
        return productService.getProductsByCustomerLastNameStartsWith(lastNamePrefix);
    }

    @GetMapping("weirdfilter")
    public List<Product> getProductsByCriteria(@RequestParam("lastNamePrefix") String lastNamePrefix,
                                               @RequestParam("maxCustomerId") Long maxCustomerId,
                                               @RequestParam("categoryName") String categoryName) {
        return productService.getProductsByCriteria(lastNamePrefix, maxCustomerId, categoryName);
    }
}
