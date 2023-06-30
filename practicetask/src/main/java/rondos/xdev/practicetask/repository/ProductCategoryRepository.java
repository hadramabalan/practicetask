package rondos.xdev.practicetask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rondos.xdev.practicetask.model.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    ProductCategory findByName(String name);
}
