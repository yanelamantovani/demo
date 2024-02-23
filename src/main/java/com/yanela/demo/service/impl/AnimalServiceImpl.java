package com.yanela.demo.service.impl;

import com.yanela.demo.domain.entity.Animal;
import com.yanela.demo.repository.AnimalRepository;
import com.yanela.demo.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class AnimalServiceImpl implements AnimalService {

	@Autowired
	private AnimalRepository animalRepository;

	@Override
	public List<Animal> getAnimals() {
		return StreamSupport.stream(animalRepository
					.findAll()
					.spliterator(),
					false)
				.toList();
	}

	@Override
	public Page<Animal> getAnimals(Pageable pageable) {
		return animalRepository.findAll(pageable);
	}

	@Override
	public Optional<Animal> getAnimal(Long id) {
		return animalRepository.findById(id);
	}

	@Override
	public Animal createAnimal(Animal animal) {
		return animalRepository.save(animal);
	}

	@Override
	public boolean animalExists(Long id) {
		return animalRepository.existsById(id);
	}

	@Override
	public Animal updateAnimal(Long id, Animal animal) {
		animal.setId(id);

		return animalRepository.findById(id).map(existingAnimal -> {
			Optional.ofNullable(animal.getName()).ifPresent(existingAnimal::setName);
			return animalRepository.save(existingAnimal);
		}).orElseThrow(()-> new RuntimeException("Animal does not exist"));
	}

	@Override
	public void deleteAnimal(Long id) {
		animalRepository.deleteById(id);
	}

}
