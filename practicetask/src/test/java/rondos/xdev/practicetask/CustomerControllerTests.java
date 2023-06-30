package rondos.xdev.practicetask;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import rondos.xdev.practicetask.model.Customer;
import rondos.xdev.practicetask.rest.CustomerController;
import rondos.xdev.practicetask.service.CustomerService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//TODO auth problems
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTests {
    @MockBean
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAllCustomersAsAdmin() throws Exception {
        List<Customer> customers = Arrays.asList(
                Customer.builder()
                        .id(1L)
                        .firstName("John")
                        .lastName("Doe")
                        .build(),
                Customer.builder()
                        .id(2L)
                        .firstName("Jane")
                        .lastName("Smith")
                        .build()
        );

        given(customerService.getAllCustomers()).willReturn(customers);

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].firstName").value("Jane"))
                .andExpect(jsonPath("$[1].lastName").value("Smith"));

        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetAllCustomersAsUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetCustomerByIdAsAdmin() throws Exception {
        Customer customer = Customer.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build();
        when(customerService.getCustomerById("1")).thenReturn(Optional.ofNullable(customer));

        if (customer != null) {
            mockMvc.perform(MockMvcRequestBuilders.get("/customers/1"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(customer.getId()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(customer.getFirstName()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(customer.getLastName()));
        } else {
            mockMvc.perform(MockMvcRequestBuilders.get("/customers/1"))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCreateCustomerAsAdmin() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType("application/json")
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"status\":\"active\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(customerService, times(1)).createCustomer("John", "Doe", "active");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteCustomerAsAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(customerService, times(1)).deleteCustomer("1L");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCreateCustomerThenDeleteAsAdmin() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType("application/json")
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"status\":\"active\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(customerService, times(1)).createCustomer("John", "Doe", "active");

        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(customerService, times(1)).deleteCustomer("1");
    }
}