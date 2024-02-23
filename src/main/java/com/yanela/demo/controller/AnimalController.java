package com.yanela.demo.controller;

import com.yanela.demo.domain.entity.Animal;
import com.yanela.demo.domain.dto.AnimalDto;
import com.yanela.demo.mappers.Mapper;
import com.yanela.demo.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AnimalController {

	@Autowired
	private AnimalService animalService;

	@Autowired
	private Mapper<Animal, AnimalDto> animalMapper;

	@GetMapping(path = "/animals")
	public Page<AnimalDto> getAnimals(Pageable pageable) {
		Page<Animal> animals = animalService.getAnimals(pageable);
		return animals.map(animalMapper::mapTo);
	}

	@GetMapping(path = "/animals/{id}")
	public ResponseEntity<AnimalDto> getAnimal(@PathVariable("id") Long id) {
		Optional<Animal> foundAnimal = animalService.getAnimal(id);
		return foundAnimal.map(animal -> {
			AnimalDto animalDto = animalMapper.mapTo(animal);
			return new ResponseEntity<>(animalDto,HttpStatus.OK);
		}).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping(path = "/animals")
	public ResponseEntity<AnimalDto> createAnimal(@RequestBody AnimalDto animalDto) {
		Animal animal = animalMapper.mapFrom(animalDto);
		Animal savedAnimal = animalService.createAnimal(animal);
		return new ResponseEntity<>(animalMapper.mapTo(savedAnimal), HttpStatus.CREATED);
	}

	@PutMapping(path = "/animals/{id}")
	public ResponseEntity<AnimalDto> fullUpdateAnimal(
			@PathVariable("id") Long id,
			@RequestBody AnimalDto animalDto) {
		if (!animalService.animalExists(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		animalDto.setId(id);
		Animal animal = animalMapper.mapFrom(animalDto);
		Animal savedAnimal = animalService.createAnimal(animal);
		return new ResponseEntity<>(
				animalMapper.mapTo(savedAnimal),
				HttpStatus.OK);
	}

	@PatchMapping(path = "/animals/{id}")
	public ResponseEntity<AnimalDto> partialUpdateAnimal(
			@PathVariable("id") Long id,
			@RequestBody AnimalDto animalDto) {
		if (!animalService.animalExists(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Animal animal = animalMapper.mapFrom(animalDto);
		Animal updatedAnimal = animalService.updateAnimal(id, animal);
		return new ResponseEntity<>(
				animalMapper.mapTo(updatedAnimal),
				HttpStatus.OK);
	}

	@DeleteMapping(path = "/animals/{id}")
	public ResponseEntity deleteAnimal(@PathVariable("id") Long id) {
		if (!animalService.animalExists(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		animalService.deleteAnimal(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
