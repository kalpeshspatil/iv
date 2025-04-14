package com.iv.iv.controller;

import com.iv.iv.entity.Product;
import com.iv.iv.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://iv.dakshabhi.com"})  // Allow CORS for React app only
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody Product productDetails) {
        Optional<Product> productOptional = productService.getProductById(productDetails.getProductId());
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setProductName(productDetails.getProductName());
            product.setProductBrand(productDetails.getProductBrand());
            product.setProductLandingPrice(productDetails.getProductLandingPrice());
            return ResponseEntity.ok(productService.saveProduct(product));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
