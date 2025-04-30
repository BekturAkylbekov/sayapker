package com.example.sayapker.api;

import com.example.sayapker.dto.request.AnimalRequest;
import com.example.sayapker.dto.response.SimpleResponse;
import com.example.sayapker.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/animal")
@RequiredArgsConstructor

public class AnimalApi {

    private final AnimalService animalService;

    @PostMapping("/create")
    public SimpleResponse createAnimal(@RequestBody AnimalRequest animalRequest) {
         return animalService.createAnimal(animalRequest);
    }
    @DeleteMapping("/delete/{id}")
    public SimpleResponse deleteSheep(@PathVariable Long id) {
        return animalService.deleteAnimal(id);
    }
    @PutMapping("/update/{id}")
    public SimpleResponse updateAnimal(@PathVariable Long id, @RequestBody AnimalRequest animalRequest) {
        return animalService.updateAnimal(id,animalRequest);
    }

}
