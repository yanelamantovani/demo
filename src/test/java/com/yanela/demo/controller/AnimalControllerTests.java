package com.yanela.demo.controller;

import com.yanela.demo.TestDataUtil;
import com.yanela.demo.domain.dto.AnimalDto;
import com.yanela.demo.domain.entity.Animal;
import com.yanela.demo.mappers.Mapper;
import com.yanela.demo.service.AnimalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class AnimalControllerTests {
	@InjectMocks
	private AnimalController animalController;
	@Mock
	private AnimalService animalService;
	@Spy
	private Mapper<Animal, AnimalDto> animalMapper;

	@Test
	public void thisIsATest() {

	}

	@Test
	public void testThatCreateAnimalSuccessfullyReturnsHttp201Created() throws Exception {
		// Preparation
		AnimalDto animalDto = TestDataUtil.createTestAnimalDto(null);
		// Do
		ResponseEntity<AnimalDto> response = animalController.createAnimal(animalDto);
		// Assertions
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}

}
