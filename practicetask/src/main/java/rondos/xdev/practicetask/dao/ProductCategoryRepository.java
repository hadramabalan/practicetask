package rondos.xdev.practicetask.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import rondos.xdev.practicetask.model.Product;

public interface ProductCategoryRepository extends JpaRepository<Product, Long> {
}
