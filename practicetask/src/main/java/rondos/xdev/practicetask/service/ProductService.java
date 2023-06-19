package rondos.xdev.practicetask.service;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import rondos.xdev.practicetask.model.Customer;
import rondos.xdev.practicetask.repository.ProductRepository;
import rondos.xdev.practicetask.model.Product;

import java.util.List;
import java.util.Map;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    //TODO
    public Product createProduct(String name, String category, Map<String, Object> optionals) {
        return null;
    }

    //TODO
    public Product updateProduct(String id, Map<String, Object> changes) {
        return null;
    }

    public void deleteProduct(String id) {
        if (productRepository.existsById(Long.parseLong(id))){
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
}