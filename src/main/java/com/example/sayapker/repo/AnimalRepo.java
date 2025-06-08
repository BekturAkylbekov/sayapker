package com.example.sayapker.repo;

import com.example.sayapker.dto.response.AnimalResponse;
import com.example.sayapker.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepo extends JpaRepository<Animal, Long> {
    @Query("select  new com.example.sayapker.dto.response.AnimalResponse(a.name,a.imageUrl,a.gender,a.country,a.price) from Animal a order by a.price asc ")
    List<AnimalResponse> sortByPriceAsc();
    @Query("select  new com.example.sayapker.dto.response.AnimalResponse(a.name,a.imageUrl,a.gender,a.country,a.price) from Animal a order by a.price desc")
    List<AnimalResponse> sortByPriceDesc();

}
