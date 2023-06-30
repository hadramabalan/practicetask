package rondos.xdev.practicetask.rest;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.*;
import rondos.xdev.practicetask.exception.ProductNotFoundException;
import rondos.xdev.practicetask.model.Product;
import rondos.xdev.practicetask.service.ProductService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private final ProductService productService;

    @Autowired
    private PlatformTransactionManager transactionManager;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @Operation(summary = "Get all products", description = "Get the details of all existing products.")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/user/{userId} OR hasRole('MANAGER')")
    @PreAuthorize("(hasAuthority('VIEW_OWN_PRODUCTS') and @securityHelper.isOwner(#userId)) OR hasRole('ADMIN') OR hasRole('MANAGER')")
    @Operation(summary = "Get products of user by id", description = "Get the details of all existing products of a user by id.")
    public Optional<List<Product>> getUserProducts(@PathVariable String userId) {
        // Retrieve user products
        // ...
        return productService.getProducts(userId);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create product", description = "Create a product with a given name, category and optionals.")
    public Product createProduct(@RequestParam String name, String category, Map<String, Object> optionals) {
        return productService.createProduct(name, category, optionals);
    }


    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update product by ID", description = "Update product details by their ID and provided details.")
    public Product updateProduct(@RequestParam String id, Map<String, Object> changes) {
        return productService.updateProduct(id, changes);
    }

    //token transaction
    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete product by ID", description = "Delete a product by their ID.")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {
        TransactionStatus transactionStatus = null;
        try {
            TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
            transactionStatus = transactionManager.getTransaction(transactionDefinition);

            productService.deleteProduct(productId);

            transactionManager.commit(transactionStatus);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (ProductNotFoundException e) {
            if (transactionStatus != null) {
                transactionManager.rollback(transactionStatus);
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            if (transactionStatus != null) {
                transactionManager.rollback(transactionStatus);
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/customers")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @Operation(summary = "Get products of customers by last name prefix", description = "Get the details of all products of customers by their last name prefix.")
    public List<Product> getProductsByCustomerLastNameStartsWith(@RequestParam("lastNamePrefix") String lastNamePrefix) {
        return productService.getProductsByCustomerLastNameStartsWith(lastNamePrefix);
    }

    @GetMapping("weirdfilter")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @Operation(summary = "Get products of customers by last name prefix/max customer id/category name", description = "Get the details of all products of customers by last name prefix/max customer id/category name. One or more parameters can be applied.")
    public List<Product> getProductsByCriteria(@RequestParam("lastNamePrefix") String lastNamePrefix,
                                               @RequestParam("maxCustomerId") Long maxCustomerId,
                                               @RequestParam("categoryName") String categoryName) {
        return productService.getProductsByCriteria(lastNamePrefix, maxCustomerId, categoryName);
    }
}
