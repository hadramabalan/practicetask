package rondos.xdev.practicetask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rondos.xdev.practicetask.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
