package rondos.xdev.practicetask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rondos.xdev.practicetask.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
