package rondos.xdev.practicetask;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import rondos.xdev.practicetask.model.Product;
import rondos.xdev.practicetask.model.ProductCategory;
import rondos.xdev.practicetask.repository.ProductRepository;
import rondos.xdev.practicetask.service.ProductService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAllProductsAsAdmin() throws Exception {
        Product product1 = Product.builder().id(1L).name("Product 1").build();
        Product product2 = Product.builder().id(2L).name("Product 2").build();
        List<Product> products = Arrays.asList(product1, product2);

        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(products.size()))
                .andExpect(jsonPath("$[0].id").value(product1.getId()))
                .andExpect(jsonPath("$[0].name").value(product1.getName()))
                .andExpect(jsonPath("$[1].id").value(product2.getId()))
                .andExpect(jsonPath("$[1].name").value(product2.getName()));

        verify(productService, times(1)).getAllProducts();
        verifyNoMoreInteractions(productService);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetAllProductsAsUser() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isForbidden());

        verifyNoInteractions(productService);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetProductByIdAsUser() throws Exception {
        Long productId = 1L;

        mockMvc.perform(get("/products/{id}", productId))
                .andExpect(status().isForbidden());

        verifyNoInteractions(productService);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCreateProductAsAdmin() throws Exception {
        Product product = Product.builder()
                .name("New Product")
                .category(ProductCategory.builder()
                        .name("Some category")
                        .build())
                .build();

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product someProduct = invocation.getArgument(0);
            someProduct.setId(1L); // Set the desired ID
            return someProduct;
        });

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Product\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(product.getName()));

        verify(productService, times(1)).createProduct(eq("New Product"), eq("Some category"), anyMap());
        verifyNoMoreInteractions(productService);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateProductAsAdmin() throws Exception {
        Long productId = 1L;
        Product existingProduct = Product.builder().id(productId).name("Product").build();
        Product updatedProduct = Product.builder().id(productId).name("Updated Product").build();

        when(productService.getProductById(productId)).thenReturn(existingProduct);
        when(productService.updateProduct(eq(String.valueOf(productId)), any(Map.class))).thenReturn(updatedProduct);

        mockMvc.perform(put("/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Product\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(updatedProduct.getId()))
                .andExpect(jsonPath("$.name").value(updatedProduct.getName()));

        verify(productService, times(1)).getProductById(productId);
        verify(productService, times(1)).updateProduct(eq(String.valueOf(productId)), any(Map.class));
        verifyNoMoreInteractions(productService);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteProductAsAdmin() throws Exception {
        String productId = "1";

        mockMvc.perform(delete("/products/{id}", productId))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(productId);
        verifyNoMoreInteractions(productService);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCreateThenDeleteProductAsAdmin() throws Exception {
        Product product = Product.builder()
                .name("New Product")
                .category(ProductCategory.builder()
                        .name("Some category")
                        .build())
                .build();

        when(productService.createProduct(any(String.class), any(String.class), any(Map.class))).thenReturn(product);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Product\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(product.getName()));

        verify(productService, times(1)).createProduct(eq("New Product"), eq("Some category"), anyMap());

        Long productId = product.getId();

        mockMvc.perform(delete("/products/{id}", productId))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(String.valueOf(productId));
        verifyNoMoreInteractions(productService);
    }
}