package rondos.xdev.practicetask.service;

import org.springframework.stereotype.Service;
import rondos.xdev.practicetask.dao.ProductEntity;
import rondos.xdev.practicetask.dao.ProductRepository;
import rondos.xdev.practicetask.model.Product;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        List<ProductEntity> productEntities = productRepository.findAll();
        return productEntities.stream()
                .map(this::mapToProduct)
                .collect(Collectors.toList());
    }

    // other service methods

    private Product mapToProduct(ProductEntity productEntity) {
        return new Product(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getCategory(),
                productEntity.getValidFrom(),
                productEntity.getValidTo()
        );
    }
}