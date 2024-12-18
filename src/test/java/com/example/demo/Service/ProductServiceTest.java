package com.example.demo.Service;

import com.example.demo.Exception.ProductNotFoundException;
import com.example.demo.Model.Product;
import com.example.demo.Repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    private ProductService productService;
    AutoCloseable autoCloseable;
    Product product;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository);
        product = new Product(10L,"Productive", "Product desc", 777);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void testGetAllProducts() {

        mock(Product.class);
        mock(ProductRepository.class);

        when(productRepository.findAll()).thenReturn(
                new ArrayList<Product>(Collections.singleton(product))
        );
        assertThat(productService.getAllProducts().get(0).getPrice())
                .isEqualTo(product.getPrice());
    }

    @Test
    void getProductById() throws ProductNotFoundException{
        mock(Product.class);
        mock(ProductRepository.class);

        when(productRepository.findById(product.getId())).thenReturn(Optional.ofNullable(product));
        assertThat(productService.getProductById(product.getId()).getName()).isEqualTo(product.getName());
    }

    @Test
    void testCreateProduct() {
        mock(Product.class);
        mock(ProductRepository.class);

        when(productRepository.save(product)).thenReturn(product);
        assertThat(productService.createProduct(product)).isEqualTo(product);
    }

    @Test
    void testUpdateProduct() throws ProductNotFoundException {
        mock(Product.class);
        mock(ProductRepository.class);
Product updatedProduct = productService.getProductById(1L);
updatedProduct.setName("new name");
        //new Product(1L, "new name", "new desc", 1000);
        when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);
        assertThat(productService.updateProduct(product.getId(), updatedProduct)).isEqualTo(updatedProduct);

    }

    @Test
    void deleteProduct() throws ProductNotFoundException{
        mock(Product.class);
        mock(ProductRepository.class, Mockito.CALLS_REAL_METHODS);

        doAnswer(Answers.CALLS_REAL_METHODS).when(productRepository).deleteById(any());
       // assertThat(productService.deleteProduct(product.getId())).isEqualTo(product.getId());
        assertThat(productService.deleteProduct(product.getId())).isEqualTo("Success");



    }
}