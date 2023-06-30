package rondos.xdev.practicetask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rondos.xdev.practicetask.model.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCustomerLastNameStartsWith(String lastNamePrefix);

    Optional<List<Product>> findAllByCustomer_Id(Long customerId);

    List<Product> findByCustomerLastNameStartsWithAndCustomerIdLessThanEqualAndCategoryName(String lastNamePrefix, Long maxCustomerId, String categoryName);
}
