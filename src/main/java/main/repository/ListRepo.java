package main.repository;

import main.model.Lists;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ListRepo extends MongoRepository<Lists, Long> {
}
