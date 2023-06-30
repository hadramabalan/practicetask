package rondos.xdev.practicetask.service;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import rondos.xdev.practicetask.exception.ProductCategoryNotFoundException;
import rondos.xdev.practicetask.exception.ProductNotFoundException;
import rondos.xdev.practicetask.model.Customer;
import rondos.xdev.practicetask.model.Product;
import rondos.xdev.practicetask.model.ProductCategory;
import rondos.xdev.practicetask.repository.ProductCategoryRepository;
import rondos.xdev.practicetask.repository.ProductRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public ProductService(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(String name, String categoryName, Map<String, Object> optionals) {
        ProductCategory category = productCategoryRepository.findByName(categoryName);
        if (category == null) {
            throw new ProductCategoryNotFoundException("Product category not found with name: " + categoryName);
        }

        Product product = Product.builder()
                .name(name)
                .category(category)
                .build();

        // Set optional attributes if provided
        if (optionals != null) {
            if (optionals.containsKey("customer")) {
                Customer customer = (Customer) optionals.get("customer");
                product.setCustomer(customer);
            }

            if (optionals.containsKey("validFrom")) {
                LocalDate validFrom = (LocalDate) optionals.get("validFrom");
                product.setValidFrom(validFrom);
            }

            if (optionals.containsKey("validTo")) {
                LocalDate validTo = (LocalDate) optionals.get("validTo");
                product.setValidTo(validTo);
            }
        }

        return productRepository.save(product);
    }

    public Product updateProduct(String id, Map<String, Object> changes) {
        Optional<Product> optionalProduct = productRepository.findById(Long.parseLong(id));
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }

        Product product = optionalProduct.get();

        // Apply changes if provided
        if (changes != null) {
            if (changes.containsKey("customer")) {
                Customer customer = (Customer) changes.get("customer");
                product.setCustomer(customer);
            }

            if (changes.containsKey("validFrom")) {
                LocalDate validFrom = (LocalDate) changes.get("validFrom");
                product.setValidFrom(validFrom);
            }

            if (changes.containsKey("validTo")) {
                LocalDate validTo = (LocalDate) changes.get("validTo");
                product.setValidTo(validTo);
            }
        }

        return productRepository.save(product);
    }

    public void deleteProduct(String id) {
        if (productRepository.existsById(Long.parseLong(id))) {
            productRepository.deleteById(Long.parseLong(id));
        } else {
            throw new ResourceNotFoundException("Customer not found");
        }
    }


    public List<Product> getProductsByCustomerLastNameStartsWith(String lastNamePrefix) {
        return productRepository.findByCustomerLastNameStartsWith(lastNamePrefix);
    }

    public List<Product> getProductsByCriteria(String lastNamePrefix, Long maxCustomerId, String categoryName) {
        return productRepository.findByCustomerLastNameStartsWithAndCustomerIdLessThanEqualAndCategoryName(
                lastNamePrefix, maxCustomerId, categoryName);
    }

    public Optional<List<Product>> getProducts(String userId) {
        return productRepository.findAllByCustomer_Id(Long.parseLong(userId));
    }


    public Product getProductById(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        return optionalProduct.orElse(null);
    }
}