package rondos.xdev.practicetask.service;

import org.springframework.stereotype.Service;
import rondos.xdev.practicetask.model.Customer;

import java.util.List;

@Service
public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer getCustomerById(Long customerId);
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Long customerId, Customer customer);
    void deleteCustomer(Long customerId);
}
