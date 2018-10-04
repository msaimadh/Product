package com.capgemini.product.test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capgemini.productapp.controller.ProductController;
import com.capgemini.productapp.entity.Product;
import com.capgemini.productapp.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductControllertest {
	
	
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
		
Product product = new Product(1111,"pen","stationary",10);
		
		when(productService.addProduct(Mockito.isA(Product.class))).thenReturn(product);

		mockMvc.perform(post("/product").                                  
				               contentType(MediaType.APPLICATION_JSON_UTF8)
				               .content("{\"productId\": 1111, \"ProductName\": \"pen\",\"productCategory\":\"stationary\",\"productPrice\":10}")
				               .accept(MediaType.APPLICATION_JSON))
		                     .andExpect(status().isOk())
		                     .andExpect(jsonPath("$.ProductId").exists())
		                     .andExpect(jsonPath("$.ProductName").exists())
		                     .andExpect(jsonPath("$.Productcategory").exists())
		                     .andExpect(jsonPath("$.productPrice").exists())
		                     .andExpect(jsonPath("$.ProductId").value("1111"))
		                     .andExpect(jsonPath("$.ProductName").value("pen"))
		                     .andExpect(jsonPath("$.Productcategory").value("stationary"))
		                     .andExpect(jsonPath("$.productPrice").value("10"))
		                     .andDo(print());		        
}
}