package com.api.service;

import com.api.entity.Product;
import com.api.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Chercher tous les produits
     */
    public Iterable<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * Chercher un produit avec l'id
     *
     * @param id id du produit à chercher
     */
    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }

    /**
     * Creation d'un produit
     *
     * @param product produit à créer
     */
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Cherche si un produit existe avec un id.
     * S'il existe, une mise à jour du produit sera faite.
     *
     * @param updatedProduct produit de mise à jour
     * @param id             id du produit à mettre à jour
     */
    public Product partialUpdateProduct(Product updatedProduct, Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            Arrays.stream(product.getClass().getDeclaredFields()).forEach(field -> {
                switch (field.getName()) {
                    case "code":
                        if (updatedProduct.getCode() != null) product.setCode(updatedProduct.getCode());
                    case "name":
                        if (updatedProduct.getName() != null) product.setName(updatedProduct.getName());
                    case "description":
                        if (updatedProduct.getDescription() != null)
                            product.setDescription(updatedProduct.getDescription());
                    case "price":
                        if (updatedProduct.getPrice() != null) product.setPrice(updatedProduct.getPrice());
                    case "quantity":
                        if (updatedProduct.getQuantity() != null) product.setQuantity(updatedProduct.getQuantity());
                    case "inventoryStatus":
                        if (updatedProduct.getInventoryStatus() != null)
                            product.setInventoryStatus(updatedProduct.getInventoryStatus());
                    case "category":
                        if (updatedProduct.getCategory() != null) product.setCategory(updatedProduct.getCategory());
                    case "image":
                        if (updatedProduct.getImage() != null) product.setImage(updatedProduct.getImage());
                    case "rating":
                        if (updatedProduct.getRating() != null) product.setRating(updatedProduct.getRating());
                }
            });
            productRepository.save(product);
            return product;
        } else {
            return null;
        }
    }

    /**
     * Trouver le produit avec id, s'il existe le supprimer
     *
     * @param id id du produit à supprimer
     */
    public void deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        product.ifPresent(productRepository::delete);
    }

    /**
     * Cherhcer tous les produit avec les ids et les supprimer
     *
     * @param ids ids des produits à supprimer
     */
    public void deleteProducts(Iterable<Long> ids) {
        Iterable<Product> products = productRepository.findAllById(ids);
        products.forEach(productRepository::delete);
    }
}
