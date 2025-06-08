package com.example.sayapker.service.serviceImpl;

import com.example.sayapker.dto.request.AnimalRequest;
import com.example.sayapker.dto.response.AnimalResponse;
import com.example.sayapker.dto.response.SimpleResponse;
import com.example.sayapker.entities.Animal;
import com.example.sayapker.entities.User;
import com.example.sayapker.exceptions.NotFoundException;
import com.example.sayapker.repo.AnimalRepo;
import com.example.sayapker.repo.UserRepo;
import com.example.sayapker.service.AnimalService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final UserRepo userRepo;
    private final AnimalRepo animalRepo;

    @Override
    public SimpleResponse createAnimal(AnimalRequest animalRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String phoneNumber = authentication.getName();

        User user = userRepo.findUserByPhoneNumber(phoneNumber).orElseThrow(()
                -> new NotFoundException("User not found"));
        Animal animal = new Animal();
        animal.setImageUrl(animalRequest.getImageUrl());
        animal.setName(animalRequest.getName());
        animal.setBirthday(animalRequest.getBirthday());
        animal.setColor(animalRequest.getColor());
        animal.setGender(animalRequest.getGender());
        animal.setCategory(animalRequest.getCategory());
        animal.setFatherId(animalRequest.getFather());
        animal.setMotherId(animalRequest.getMother());
        animal.setNameOwner(animalRequest.getNameOwner());
        animal.setCountry(animalRequest.getCountry());
        animal.setExtraInfo(animalRequest.getExtraInfo());
        animal.setPayment(animalRequest.getPayment());
        animal.setPrice(animalRequest.getPrice());
        animal.setUser(user);
        animalRepo.save(animal);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Animal created")
                .build();
    }

    @Override
    public SimpleResponse deleteAnimal(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String phoneNumber = authentication.getName();

        User user = userRepo.findUserByPhoneNumber(phoneNumber).orElseThrow(()
                -> new NotFoundException("User not found"));
        Animal animal = animalRepo.findById(id).orElseThrow(() -> new NotFoundException("Animal not found"));
        animal.setUser(null);
        animalRepo.delete(animal);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("deleted")
                .build();
    }

    @Override
    public SimpleResponse updateAnimal(Long id, AnimalRequest animalRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String phoneNumber = authentication.getName();
        Animal animal = animalRepo.findById(id).orElseThrow(()
                -> new NotFoundException("Animal not found"));
        animal.setImageUrl(animalRequest.getImageUrl());
        animal.setName(animalRequest.getName());
        animal.setBirthday(animalRequest.getBirthday());
        animal.setColor(animalRequest.getColor());
        animal.setGender(animalRequest.getGender());
        animal.setCategory(animalRequest.getCategory());
        animal.setFatherId(animalRequest.getFather());
        animal.setMotherId(animalRequest.getMother());
        animal.setCountry(animalRequest.getCountry());
        animal.setPrice(animalRequest.getPrice());

        animalRepo.save(animal);

        return SimpleResponse
                .builder()
                .status(HttpStatus.OK)
                .message("Animal updated")
                .build();
    }

    @Override
    public List<AnimalResponse> sortByPrice(String order) {
        if (order.equals("asc")) {
            return animalRepo.sortByPriceAsc();
        } else {
            return animalRepo.sortByPriceDesc();
        }
    }
}