package com.example.sayapker.repo;

import com.example.sayapker.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
   Optional <User> findUserByEmail(String email);
   Optional <User> findUserByPhoneNumber(String phoneNumber);
   boolean existsUserByEmail(String email);
}
