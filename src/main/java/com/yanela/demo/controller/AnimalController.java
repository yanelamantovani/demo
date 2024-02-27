package com.yanela.demo.controller;

import com.yanela.demo.domain.entity.Animal;
import com.yanela.demo.domain.dto.AnimalDto;
import com.yanela.demo.mappers.Mapper;
import com.yanela.demo.service.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/demo/animals")
@Tag(name = "Animals")
public class AnimalController {

	@Autowired
	private AnimalService animalService;

	@Autowired
	private Mapper<Animal, AnimalDto> animalMapper;

	@Operation(
			description = "Get all animals",
			summary = "Get all animals",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK"
					)
			}
	)
	@GetMapping
	public Page<AnimalDto> getAnimals(Pageable pageable) {
		Page<Animal> animals = animalService.getAnimals(pageable);
		return animals.map(animalMapper::mapTo);
	}

	@Operation(
			description = "Get animal by ID",
			summary = "Get animal by ID",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK"
					),
					@ApiResponse(
							responseCode = "404",
							description = "Not Found"
					)
			}
	)
	@GetMapping(path = "/{id}")
	public ResponseEntity<AnimalDto> getAnimal(@PathVariable("id") Long id) {
		Optional<Animal> foundAnimal = animalService.getAnimal(id);
		return foundAnimal.map(animal -> {
			AnimalDto animalDto = animalMapper.mapTo(animal);
			return new ResponseEntity<>(animalDto,HttpStatus.OK);
		}).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@Operation(
			description = "Create new animal",
			summary = "Create new animal",
			responses = {
					@ApiResponse(
							responseCode = "201",
							description = "Created"
					)
			}
	)
	@PostMapping
	public ResponseEntity<AnimalDto> createAnimal(@RequestBody AnimalDto animalDto) {
		Animal animal = animalMapper.mapFrom(animalDto);
		Animal savedAnimal = animalService.createAnimal(animal);
		return new ResponseEntity<>(animalMapper.mapTo(savedAnimal), HttpStatus.CREATED);
	}

	@Operation(
			description = "Update an animal by ID",
			summary = "Update an animal by ID",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK"
					),
					@ApiResponse(
							responseCode = "404",
							description = "Not Found"
					)
			}
	)
	@PutMapping(path = "/{id}")
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

	@Operation(
			description = "Update some attributes of an animal by ID",
			summary = "Update some attributes of an animal by ID",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK"
					),
					@ApiResponse(
							responseCode = "404",
							description = "Not Found"
					)
			}
	)
	@PatchMapping(path = "/{id}")
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

	@Operation(
			description = "Delete animal by ID",
			summary = "Delete animal by ID",
			responses = {
					@ApiResponse(
							responseCode = "204",
							description = "No Content"
					),
					@ApiResponse(
							responseCode = "404",
							description = "Not Found"
					)
			}
	)
	@DeleteMapping(path = "/{id}")
	public ResponseEntity deleteAnimal(@PathVariable("id") Long id) {
		if (!animalService.animalExists(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		animalService.deleteAnimal(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
