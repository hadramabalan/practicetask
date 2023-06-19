package rondos.xdev.practicetask.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import rondos.xdev.practicetask.model.Customer;
import rondos.xdev.practicetask.model.Status;
import rondos.xdev.practicetask.repository.CustomerRepository;

import java.util.List;
import java.util.Map;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    //TODO check return value
    public Customer createCustomer(String firstName, String lastName, String status) {
        Customer customer = Customer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .status(Status.valueOf(status))
                .build();
        customerRepository.save(customer);
        return customer;
    }

    //TODO check proper return value
    public Customer updateCustomer(String customerId, Map<String, Object> changes) {
        Customer customer = customerRepository.findById(Long.parseLong(customerId))
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + customerId));
        //TODO should produce error if no changes are present (check here or up one layer?)
        if (changes.containsKey("firstName")) {
            customer.setFirstName((String) changes.get("firstName"));
        }
        if (changes.containsKey("lastName")) {
            customer.setLastName((String) changes.get("lastName"));
        }
        if (changes.containsKey("status")) {
            customer.setStatus(Status.valueOf((String) changes.get("status")));
        }
        return customerRepository.save(customer);
    }

    //TODO option for mass deletion?
    public void deleteCustomer(String customerId) {
        if (customerRepository.existsById(Long.parseLong(customerId))){
        customerRepository.deleteById(Long.parseLong(customerId));
        } else {
            throw new ResourceNotFoundException("Customer not found");
        }
    }

    //TODO errors
    public List<Customer> getCustomersWithFilters(Map<String, Object> filters) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase();

        Customer customerExample = new Customer();

        if (filters.containsKey("minProducts")) {
            customerExample.setProductsSizeMin(Integer.parseInt((String) filters.get("minProducts")));
        }

        if (filters.containsKey("maxProducts")) {
            customerExample.setProductsSizeMax(Integer.parseInt((String) filters.get("maxProducts")));
        }

        if (filters.containsKey("exactProducts")) {
            customerExample.setProductsSize(Integer.parseInt((String) filters.get("exactProducts")));
        }

        if (filters.containsKey("idLessThan")) {
            customerExample.setIdLessThan(Long.parseLong((String) filters.get("idLessThan")));
        }

        if (filters.containsKey("idLessThanOrEqual")) {
            customerExample.setIdLessThanOrEqualTo(Long.parseLong((String) filters.get("idLessThanOrEqual")));
        }

        if (filters.containsKey("idMoreThan")) {
            customerExample.setIdGreaterThan(Long.parseLong((String) filters.get("idMoreThan")));
        }

        if (filters.containsKey("idMoreThanOrEqual")) {
            customerExample.setIdGreaterThanOrEqualTo(Long.parseLong((String) filters.get("idMoreThanOrEqual")));
        }

        if (filters.containsKey("id")) {
            customerExample.setId(Long.parseLong((String) filters.get("id")));
        }

        if (filters.containsKey("firstName")) {
            customerExample.setFirstName((String) filters.get("firstName"));
        }

        if (filters.containsKey("lastName")) {
            customerExample.setLastName((String) filters.get("lastName"));
        }

        if (filters.containsKey("status")) {
            customerExample.setStatus(Status.valueOf((String) filters.get("status")));
        }

        Example<Customer> example = Example.of(customerExample, matcher);

        return customerRepository.findAll(example);
    }
}

