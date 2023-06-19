package rondos.xdev.practicetask;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import rondos.xdev.practicetask.rest.CustomerController;
import rondos.xdev.practicetask.service.CustomerService;

//TODO auth problems
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {
    @MockBean
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetCustomerById() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();


        String responseBody = result.getResponse().getContentAsString();

        System.out.println(responseBody);
    }
}