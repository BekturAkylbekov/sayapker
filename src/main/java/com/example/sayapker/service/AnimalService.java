package com.example.sayapker.service;

import com.example.sayapker.dto.request.AnimalRequest;
import com.example.sayapker.dto.response.SimpleResponse;

public interface AnimalService {
    SimpleResponse createAnimal(AnimalRequest animalRequest);
    SimpleResponse deleteAnimal(Long id);
    SimpleResponse updateAnimal(Long id, AnimalRequest animalRequest);
}

