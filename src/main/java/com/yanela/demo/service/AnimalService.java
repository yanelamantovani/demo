package com.yanela.demo.service;

import com.yanela.demo.domain.entity.Animal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AnimalService {

	List<Animal> getAnimals();

	Page<Animal> getAnimals(Pageable pageable);

	Optional<Animal> getAnimal(Long id);

	Animal createAnimal(Animal animal);

	boolean animalExists(Long id);

	Animal updateAnimal(Long id, Animal animal);

	void deleteAnimal(Long id);

}
