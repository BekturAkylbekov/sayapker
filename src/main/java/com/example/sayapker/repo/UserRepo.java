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
   boolean existsUserByPhoneNumber(String phone);
   Optional <User> findUserByPhoneNumber(String phoneNumber);
   @Query("Select u  from  User u")
   List<User> getAllUsers();
   Optional <User>  findUserByUserName(String name);
}
