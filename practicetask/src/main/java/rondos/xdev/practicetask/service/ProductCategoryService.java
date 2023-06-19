package rondos.xdev.practicetask.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import rondos.xdev.practicetask.model.ProductCategory;
import rondos.xdev.practicetask.repository.ProductCategoryRepository;

import java.util.List;

@Service
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public List<ProductCategory> getAllProductCategories() {
        return productCategoryRepository.findAll();
    }

    public void deleteProductCategory(String id) {
        if (productCategoryRepository.existsById(Long.parseLong(id))) {
            productCategoryRepository.deleteById(Long.parseLong(id));
        } else {
            throw new ResourceNotFoundException("Product category not found");
        }
    }

    public void createProductCategory(String name) {
        productCategoryRepository.save(ProductCategory.builder()
                .name(name)
                .build());
    }

    public void updateProductCategory(String id, String name) {
        ProductCategory productCategory = productCategoryRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new EntityNotFoundException("Product category not found with id: " + id));
        productCategory.setName(name);
        productCategoryRepository.save(productCategory);
    }
}

