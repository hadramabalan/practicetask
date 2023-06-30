package rondos.xdev.practicetask.rest;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import rondos.xdev.practicetask.model.Customer;
import rondos.xdev.practicetask.service.CustomerService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @Operation(summary = "Get all customers", description = "Get the details of all existing customers.")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{userId}")
    @PreAuthorize("(hasAuthority('VIEW_OWN_DETAILS') and @securityHelper.isOwner(#userId)) OR hasRole('ADMIN') OR hasRole('MANAGER')")
    public Optional<Customer> getUserDetails(@PathVariable String userId) {
        return customerService.getCustomerById(userId);
    }

    //TODO less than and greater than filters dont work for some reason
    @GetMapping("/filter")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @Operation(summary = "Get customers using a filter", description = "Get customer details by their ID, number of products assigned, " +
            "first name, last name, status or a combination of any of those.")
    public List<Customer> getCustomersWithFilters(@RequestParam Map<String, Object> filters) {
        return customerService.getCustomersWithFilters(filters);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete customer by ID", description = "Delete a customer by their ID.")
    public void deleteCustomer(@RequestParam String id) {
        customerService.deleteCustomer(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create customer", description = "Create a customer with a given first name, last name and status.")
    public Customer createCustomer(@RequestParam String firstName, String lastName, String status) {
        return customerService.createCustomer(firstName, lastName, status);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update customer by ID", description = "Update customer details by their ID and provided details.")
    public Customer updateCustomer(@RequestParam String id, Map<String, Object> changes) {
        return customerService.updateCustomer(id, changes);
    }
}
