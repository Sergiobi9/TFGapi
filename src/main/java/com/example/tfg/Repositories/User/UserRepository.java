package com.example.tfg.Repositories.User;

import com.example.tfg.Entities.User.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findUserByEmail(String email);
    User findUserById(String userId);
    boolean existsUserByEmail(String email);
}
