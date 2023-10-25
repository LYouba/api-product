package com.api.controller;

import com.api.entity.Product;
import com.api.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@CrossOrigin("*")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * récupérer un produit par id.
     *
     * @param id id du produit à récupérer
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getProduct(@PathVariable Long id) {
        Optional<Product> optionalProduct = productService.getProduct(id);
        return optionalProduct.<ResponseEntity<Object>>map(ResponseEntity::ok).orElseGet(
                () -> new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    /**
     * Récupérer tous les produits
     */
    @GetMapping
    public ResponseEntity<Object> getProducts() {
        Iterable<Product> products = productService.getProducts();
        if (products.iterator().hasNext()) return ResponseEntity.ok(products);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Créer un produit
     *
     * @param product produit à ajouter
     */
    @PostMapping
    public ResponseEntity<Object> saveProduct(@RequestBody Product product) {
        try {
            return new ResponseEntity<>(productService.saveProduct(product), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * mise à jour partielle un produit
     *
     * @param updatedProduct produit de mise à jour
     * @param id             id produit à mettre à jour
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Object> partialUpdateProduct(@RequestBody Product updatedProduct, @PathVariable Long id) {
        Product product = productService.partialUpdateProduct(updatedProduct, id);
        if (product != null) return new ResponseEntity<>(product, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Supprimer un produit par id
     *
     * @param id id du produit à supprimer
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Supprimer plusieurs produits par id
     *
     * @param ids tableau contenant les ids des produits à supprimer
     */
    @DeleteMapping
    public ResponseEntity<Object> deleteProducts(@RequestBody Long[] ids) {
        try {
            productService.deleteProducts(Arrays.asList(ids));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
