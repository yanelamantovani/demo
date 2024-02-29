package com.yanela.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanela.demo.TestDataUtil;
import com.yanela.demo.domain.dto.AnimalDto;
import com.yanela.demo.domain.entity.Animal;
import com.yanela.demo.service.AnimalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AnimalControllerIntegrationTests {

	@Autowired
	private AnimalService animalService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void testThatCreateAnimalSuccessfullyReturnsHttp201Created() throws Exception {
		AnimalDto animalDto = TestDataUtil.createTestAnimalDto(null);
		String animalJson = objectMapper.writeValueAsString(animalDto);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/demo/animals")
						.contentType(MediaType.APPLICATION_JSON)
						.content(animalJson)
		).andExpect(
				MockMvcResultMatchers.status().isCreated()
		);
	}

	@Test
	public void testThatCreateAnimalSuccessfullyReturnsSavedAnimal() throws Exception {
		AnimalDto animalDto = TestDataUtil.createTestAnimalDto(null);
		String animalJson = objectMapper.writeValueAsString(animalDto);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/demo//animals")
						.contentType(MediaType.APPLICATION_JSON)
						.content(animalJson)
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.id").isNumber()
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.name").value("Tom Nook")
		);
	}

	@Test
	public void testThatGetAnimalsReturnsHttpStatus200Ok() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/demo/animals")
						.contentType(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testThatGetAnimalsReturnsListOfAnimals() throws Exception {
		Animal animal = TestDataUtil.createTestAnimalA(null);
		animalService.createAnimal(animal);

		mockMvc.perform(
				MockMvcRequestBuilders.get("/demo/animals")
						.contentType(MediaType.APPLICATION_JSON)
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.content[0].id").isNumber()
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.content[0].name").value("Tom Nook")
		);
	}

	@Test
	public void testThatGetAnimalReturnsHttpStatus200WhenAnimalExists() throws Exception {
		Animal animal = TestDataUtil.createTestAnimalA(null);
		animalService.createAnimal(animal);

		mockMvc.perform(
				MockMvcRequestBuilders.get("/demo/animals/1")
						.contentType(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testThatGetAnimalReturnsHttpStatus404WhenNoAnimalExist() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/demo/animals/1")
						.contentType(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void testThatGetAnimalReturnsAnimalWhenItExists() throws Exception {
		Animal animal = TestDataUtil.createTestAnimalA(null);
		animalService.createAnimal(animal);

		mockMvc.perform(
				MockMvcRequestBuilders.get("/demo/animals/1")
						.contentType(MediaType.APPLICATION_JSON)
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.id").value(1)
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.name").value("Tom Nook")
		);
	}

	@Test
	public void testThatFullUpdateAnimalReturnsHttpStatus200WhenAnimalExists() throws Exception {
		Animal animal = TestDataUtil.createTestAnimalA(null);
		Animal savedAnimal = animalService.createAnimal(animal);

		AnimalDto animalDto = TestDataUtil.createTestAnimalDto(null);
		String animalJson = objectMapper.writeValueAsString(animalDto);

		mockMvc.perform(
				MockMvcRequestBuilders.put("/demo/animals/" + savedAnimal.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(animalJson)
		).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testThatFullUpdateAnimalReturnsHttpStatus404WhenNoAnimalExist() throws Exception {
		AnimalDto animalDto = TestDataUtil.createTestAnimalDto(null);
		String animalJson = objectMapper.writeValueAsString(animalDto);

		mockMvc.perform(
				MockMvcRequestBuilders.put("/demo/animals/99")
						.contentType(MediaType.APPLICATION_JSON)
						.content(animalJson)
		).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void testThatFullUpdateAnimalUpdatesExistingAnimal() throws Exception {
		Animal animal = TestDataUtil.createTestAnimalB(null);
		Animal savedAnimal = animalService.createAnimal(animal);

		AnimalDto animalDto = TestDataUtil.createTestAnimalDto(null);
		animalDto.setId(savedAnimal.getId());
		String animalJson = objectMapper.writeValueAsString(animalDto);

		mockMvc.perform(
				MockMvcRequestBuilders.put("/demo/animals/" + savedAnimal.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(animalJson)
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.id").value(savedAnimal.getId())
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.name").value(animalDto.getName())
		);
	}

	@Test
	public void testThatPartialUpdateAnimalReturnsHttpStatus200WhenAnimalExists() throws Exception {
		Animal animal = TestDataUtil.createTestAnimalA(null);
		Animal savedAnimal = animalService.createAnimal(animal);

		AnimalDto animalDto = TestDataUtil.createTestAnimalDto(null);
		animalDto.setName("Tom Nook Updated");
		String animalJson = objectMapper.writeValueAsString(animalDto);

		mockMvc.perform(
				MockMvcRequestBuilders.patch("/demo/animals/" + savedAnimal.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(animalJson)
		).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testThatPartialUpdateAnimalReturnsHttpStatus404WhenNoAnimalExist() throws Exception {
		AnimalDto animalDto = TestDataUtil.createTestAnimalDto(null);
		animalDto.setName("Tom Nook Updated");
		String animalJson = objectMapper.writeValueAsString(animalDto);

		mockMvc.perform(
				MockMvcRequestBuilders.patch("/demo/animals/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(animalJson)
		).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void testThatPartialUpdateAnimalUpdatesExistingAnimal() throws Exception {
		Animal animal = TestDataUtil.createTestAnimalA(null);
		Animal savedAnimal = animalService.createAnimal(animal);

		AnimalDto animalDto = TestDataUtil.createTestAnimalDto(null);
		animalDto.setId(savedAnimal.getId());
		animalDto.setName("Tom Nook Updated");
		String animalJson = objectMapper.writeValueAsString(animalDto);

		mockMvc.perform(
				MockMvcRequestBuilders.patch("/demo/animals/" + savedAnimal.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(animalJson)
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.id").value(savedAnimal.getId())
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.name").value(animalDto.getName())
		);
	}

	@Test
	public void testThatDeleteAnimalReturnsHttpStatus204WhenAnimalExists() throws Exception {
		Animal animal = TestDataUtil.createTestAnimalA(null);
		animalService.createAnimal(animal);

		mockMvc.perform(
				MockMvcRequestBuilders.delete("/demo/animals/1")
						.contentType(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	public void testThatDeleteAnimalReturnsHttpStatus404WhenNoAnimalExist() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.delete("/demo/animals/1")
						.contentType(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

}
