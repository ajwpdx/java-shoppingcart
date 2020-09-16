package com.lambdaschool.shoppingcart.controllers;

import com.lambdaschool.shoppingcart.models.Product;
import com.lambdaschool.shoppingcart.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController
{
    @Autowired
    private ProductService productService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/products", produces = {"application/json"})
    public ResponseEntity<?> listAllProducts()
    {
        List<Product> myProducts = productService.findAll();
        return new ResponseEntity<>(myProducts, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/product/{productId}",
            produces = {"application/json"})
    public ResponseEntity<?> getProductById(
            @PathVariable
                    Long productId)
    {
        Product p = productService.findProductById(productId);
        return new ResponseEntity<>(p,
                                    HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/product")
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product newproduct)
    {
        newproduct.setProductid(0);
        newproduct = productService.save(newproduct);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newProductURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userid}")
                .buildAndExpand(newproduct.getProductid())
                .toUri();
        responseHeaders.setLocation(newProductURI);

        return new ResponseEntity<>(null,
                                    responseHeaders,
                                    HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(value = "/product/{productid}")
    public ResponseEntity<?> updateProductById(
            @RequestBody Product updateProduct,
            @PathVariable long productid)
    {
        productService.update(productid, updateProduct);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/product/{productid}")
    public ResponseEntity<?> getProductById(
            @PathVariable long productid)
    {
        productService.delete(productid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
