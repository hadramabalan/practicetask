package rondos.xdev.practicetask.dao;

import org.springframework.stereotype.Repository;
import rondos.xdev.practicetask.model.Customer;

import java.util.List;

@Repository
public interface CustomerRepository {
    List<Customer> getAllCustomers();

    Customer getCustomerById(Long customerId);

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Long customerId, Customer customer);

    void deleteCustomer(Long customerId);
}
