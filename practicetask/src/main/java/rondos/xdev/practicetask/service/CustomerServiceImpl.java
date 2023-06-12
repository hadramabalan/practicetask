package rondos.xdev.practicetask.service;

import org.springframework.stereotype.Service;
import org.jooq.DSLContext;
import rondos.xdev.practicetask.dao.CustomerRepository;
import rondos.xdev.practicetask.model.Customer;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final DSLContext dslContext;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(DSLContext dslContext, CustomerRepository customerRepository) {
        this.dslContext = dslContext;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    @Override
    public Customer getCustomerById(Long customerId) {
        return customerRepository.getCustomerById(customerId);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.createCustomer(customer);
    }

    @Override
    public Customer updateCustomer(Long customerId, Customer customer) {
        return customerRepository.updateCustomer(customerId, customer);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        customerRepository.deleteCustomer(customerId);
    }
}
