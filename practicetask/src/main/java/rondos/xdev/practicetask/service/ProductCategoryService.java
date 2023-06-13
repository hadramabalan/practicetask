package rondos.xdev.practicetask.service;

import org.springframework.stereotype.Service;
import rondos.xdev.practicetask.dao.ProductCategoryRepository;

@Service
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

}

