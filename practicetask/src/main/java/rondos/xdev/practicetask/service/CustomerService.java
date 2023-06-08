package rondos.xdev.practicetask.service;

import org.springframework.stereotype.Service;
import rondos.xdev.practicetask.dao.CustomerEntity;
import rondos.xdev.practicetask.dao.CustomerRepository;
import rondos.xdev.practicetask.model.Customer;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        List<CustomerEntity> customerEntities = customerRepository.findAll();
        return customerEntities.stream()
                .map(this::mapToCustomer)
                .collect(Collectors.toList());
    }

    // other service methods

    private Customer mapToCustomer(CustomerEntity customerEntity) {
        return new Customer(
                customerEntity.getId(),
                customerEntity.getFirstName(),
                customerEntity.getLastName(),
                customerEntity.getStatus()
        );
    }
}
