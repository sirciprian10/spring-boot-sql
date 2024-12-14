package com.example.demo.Service;

import com.example.demo.Exception.InvalidDataException;
import com.example.demo.Exception.ProductNotFoundException;
import com.example.demo.Model.Product;
import com.example.demo.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        if (productRepository.findById(id).isEmpty()) {
            throw new ProductNotFoundException("There is no product with the id that you entered");
        }

            return productRepository.findById(id).get();

    }

    public Product createProduct(Product product) {
        if(product.getPrice() <= 0){
            throw new InvalidDataException("The price should be greater than zero.");
        }

        if(product.getName().isEmpty() || product.getDescription().isEmpty() ){
            throw new InvalidDataException("The name and description cannot be empty.");
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {

        if(updatedProduct.getPrice() <= 0){
            throw new InvalidDataException("The price should be greater than zero.");
        }

        if(updatedProduct.getName().isEmpty() || updatedProduct.getDescription().isEmpty() ){
            throw new InvalidDataException("The name and description cannot be empty.");
        }

        Product product = getProductById(id);
        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {


        if(productRepository.findById(id).isEmpty()){
            throw new ProductNotFoundException(
                    "There is no product with the id that you entered");
        }

            productRepository.deleteById(id);

        }

    }

