package rondos.xdev.practicetask.service;

import org.springframework.stereotype.Service;
import rondos.xdev.practicetask.dao.CustomerRepository;
import rondos.xdev.practicetask.model.Customer;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return null;
    }

    public Customer getCustomerById(Long customerId) {
        return null;
    }

    public Customer createCustomer(Customer customer) {
        return null;
    }

    public Customer updateCustomer(Long customerId, Customer customer) {
         return null;
    }

    public void deleteCustomer(Long customerId) {
    }
}
