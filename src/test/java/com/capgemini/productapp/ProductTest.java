package com.capgemini.productapp;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.capgemini.productapp.controller.ProductController;
import com.capgemini.productapp.entity.Product;
import com.capgemini.productapp.service.ProductService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ProductTest {
	
	@Mock
	private ProductService productService;
	
	@InjectMocks
	private ProductController productController;
	
	private MockMvc mockMvc;
	
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}
		
	@Test
	public void testAddProductWhichAddsProductObject() throws Exception {
		
		Product product = new Product(1111, "pen","stationary",10.0);
		
		when(productService.addProduct(Mockito.isA(Product.class))).thenReturn(product);
		
		mockMvc.perform(post("/product").
				               contentType(MediaType.APPLICATION_JSON_UTF8)
				               .content("{\"productId\": 1111, \"productName\": \"pen\",\"productCategory\": \"stationary\",\"productPrice\":10.0}")
				               .accept(MediaType.APPLICATION_JSON))
		                     .andExpect(status().isOk())
		                     .andExpect(jsonPath("$.productId").exists())
		                     .andExpect(jsonPath("$.productName").exists())
		                     .andExpect(jsonPath("$.productCategory").exists())
		                     .andExpect(jsonPath("$.productPrice").exists())
		                     
		                      .andExpect(jsonPath("$.productId").value("1111"))
		                     .andExpect(jsonPath("$.productName").value("pen"))
		                     .andExpect(jsonPath("$.productCategory").value("stationary"))
		                     .andExpect(jsonPath("$.productPrice").value("10.0"))
		                     
		                     
		                     .andDo(print());		              
	}
	
	@Test
	public void testfindproductbyId() throws Exception {
		when(productService.findProductById(1111)).thenReturn(new Product(1111, "marker", "stationary", 100.0));
		mockMvc.perform(get("/products/1111").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
				.andExpect(jsonPath("$.productId").exists())
				.andExpect(jsonPath("$.productName").exists())
				.andExpect(jsonPath("$.productCategory").exists())
				.andExpect(jsonPath("$.productPrice").exists())
				.andExpect(jsonPath("$.productId").value("1111"))
				.andExpect(jsonPath("$.productName").value("marker"))
				.andExpect(jsonPath("$.productCategory").value("stationary"))
				.andExpect(jsonPath("$.productPrice").value("100.0"));

}

@Test
public void testupdateProduct() throws Exception {

	when(productService.updateProduct(Mockito.isA(Product.class))).thenReturn(new Product(1111, "marker", "stationary", 100.0));
	when(productService.findProductById(1111)).thenReturn(new Product(1111, "pen", "stationary", 10.0));
	String content = "{\r\n" + "	\"productId\":1111,\r\n" + "	\"productName\":\"pen\",\r\n"
			+ "	\"productCategory\":\"stationary\",\r\n" + "	\"productPrice\":10.0\r\n" + "}";

	mockMvc.perform(put("/product").contentType(MediaType.APPLICATION_JSON).content(content)
			.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
			.andExpect(jsonPath("$.productId").exists())
			.andExpect(jsonPath("$.productName").exists())
			.andExpect(jsonPath("$.productCategory").exists())
			.andExpect(jsonPath("$.productPrice").exists())
			.andExpect(jsonPath("$.productId").value(1111))
			.andExpect(jsonPath("$.productName").value("marker"))
			.andExpect(jsonPath("$.productCategory").value("stationary"))
			.andExpect(jsonPath("$.productPrice").value(100.0))
			.andDo(print());

}

@Test
public void testDelete() throws Exception {
	when(productService.findProductById(1111)).thenReturn(new Product(1111, "pen", "stationary", 50.0));
	mockMvc.perform(delete("/products/1111")
			.accept(MediaType.APPLICATION_JSON))
	.andExpect(status().isOk())
	.andDo(print());
}
}

