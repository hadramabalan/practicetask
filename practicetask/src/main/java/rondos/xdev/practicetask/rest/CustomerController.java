package rondos.xdev.practicetask.rest;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import rondos.xdev.practicetask.model.Customer;
import rondos.xdev.practicetask.service.CustomerService;

import java.util.List;
import java.util.Map;

@Service
@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    //TODO less than and greater than filters dont work for some reason
    @GetMapping("/filter")
    public List<Customer> getCustomersWithFilters(@RequestParam Map<String, Object> filters) {
        return customerService.getCustomersWithFilters(filters);
    }

    @DeleteMapping
    public void deleteCustomer(@RequestParam String id) {
        customerService.deleteCustomer(id);
    }

    //TODO might have trouble returning customer, check
    @PostMapping
    public Customer createCustomer(@RequestParam String firstName, String lastName, String status) {
        return customerService.createCustomer(firstName, lastName, status);
    }

    //TODO make params mandatory
    //TODO check proper return value
    //TODO ask - should i have both a post and a put mapping for update?
    @PutMapping
    public Customer updateCustomer(@RequestParam String id, Map<String, Object> changes) {
        return customerService.updateCustomer(id, changes);
    }
}
