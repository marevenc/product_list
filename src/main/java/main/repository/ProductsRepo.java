package main.repository;

import main.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductsRepo extends MongoRepository<Product, Long> {
}
