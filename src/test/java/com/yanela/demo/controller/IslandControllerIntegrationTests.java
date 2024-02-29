package com.yanela.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanela.demo.TestDataUtil;
import com.yanela.demo.domain.dto.IslandDto;
import com.yanela.demo.domain.entity.Island;
import com.yanela.demo.service.IslandService;
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
public class IslandControllerIntegrationTests {

	@Autowired
	private IslandService islandService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void testThatCreateIslandSuccessfullyReturnsHttp201Created() throws Exception {
		Island islandA = TestDataUtil.createTestIslandA();
		islandA.setId(null);
		String islandJson = objectMapper.writeValueAsString(islandA);
		
		mockMvc.perform(
				MockMvcRequestBuilders.post("/demo/islands")
						.contentType(MediaType.APPLICATION_JSON)
						.content(islandJson)
		).andExpect(
				MockMvcResultMatchers.status().isCreated()
		);
	}

	@Test
	public void testThatCreateIslandSuccessfullyReturnsSavedAuthor() throws Exception {
		Island islandA = TestDataUtil.createTestIslandA();
		islandA.setId(null);
		String islandJson = objectMapper.writeValueAsString(islandA);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/demo/islands")
						.contentType(MediaType.APPLICATION_JSON)
						.content(islandJson)
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.id").isNumber()
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.name").value("Cute Paradise")
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.population").value("12")
		);
	}

	@Test
	public void testThatGetIslandsReturnsHttpStatus200Ok() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/demo/islands")
						.contentType(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testThatGetIslandsReturnsListOfIslands() throws Exception {
		Island island = TestDataUtil.createTestIslandA();
		islandService.createIsland(island);

		mockMvc.perform(
				MockMvcRequestBuilders.get("/demo/islands")
						.contentType(MediaType.APPLICATION_JSON)
		).andExpect(
				MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
		).andExpect(
				MockMvcResultMatchers.jsonPath("$[0].name").value("Cute Paradise")
		).andExpect(
				MockMvcResultMatchers.jsonPath("$[0].population").value("12")
		);
	}

	@Test
	public void testThatGetIslandReturnsHttpStatus200WhenIslandExists() throws Exception {
		Island island = TestDataUtil.createTestIslandA();
		islandService.createIsland(island);

		mockMvc.perform(
				MockMvcRequestBuilders.get("/demo/islands/1")
						.contentType(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testThatGetIslandReturnsHttpStatus404WhenNoIslandExist() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/demo/islands/1")
						.contentType(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void testThatGetIslandReturnsIslandWhenItExists() throws Exception {
		Island island = TestDataUtil.createTestIslandA();
		islandService.createIsland(island);

		mockMvc.perform(
				MockMvcRequestBuilders.get("/demo/islands/1")
						.contentType(MediaType.APPLICATION_JSON)
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.id").value(1)
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.name").value("Cute Paradise")
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.population").value("12")
		);
	}

	@Test
	public void testThatFullUpdateIslandReturnsHttpStatus200WhenIslandExists() throws Exception {
		Island island = TestDataUtil.createTestIslandA();
		Island savedIsland = islandService.createIsland(island);

		IslandDto islandDto = TestDataUtil.createTestIslandDto();
		String islandJson = objectMapper.writeValueAsString(islandDto);

		mockMvc.perform(
				MockMvcRequestBuilders.put("/demo/islands/" + savedIsland.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(islandJson)
		).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testThatFullUpdateIslandReturnsHttpStatus404WhenNoIslandExist() throws Exception {
		IslandDto islandDto = TestDataUtil.createTestIslandDto();
		String islandJson = objectMapper.writeValueAsString(islandDto);

		mockMvc.perform(
				MockMvcRequestBuilders.put("/demo/islands/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(islandJson)
		).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void testThatFullUpdateIslandUpdatesExistingIsland() throws Exception {
		Island island = TestDataUtil.createTestIslandB();
		Island savedIsland = islandService.createIsland(island);

		IslandDto islandDto = TestDataUtil.createTestIslandDto();
		islandDto.setId(savedIsland.getId());
		String islandJson = objectMapper.writeValueAsString(islandDto);

		mockMvc.perform(
				MockMvcRequestBuilders.put("/demo/islands/" + savedIsland.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(islandJson)
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.id").value(savedIsland.getId())
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.name").value(islandDto.getName())
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.owner").value(islandDto.getOwner())
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.population").value(islandDto.getPopulation())
		);


	}

	@Test
	public void testThatPartialUpdateIslandReturnsHttpStatus200WhenIslandExists() throws Exception {
		Island island = TestDataUtil.createTestIslandA();
		Island savedIsland = islandService.createIsland(island);

		IslandDto islandDto = TestDataUtil.createTestIslandDto();
		islandDto.setName("Cute Paradise Updated");
		String islandJson = objectMapper.writeValueAsString(islandDto);

		mockMvc.perform(
				MockMvcRequestBuilders.patch("/demo/islands/" + savedIsland.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(islandJson)
		).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testThatPartialUpdateIslandReturnsHttpStatus404WhenNoIslandExist() throws Exception {
		IslandDto islandDto = TestDataUtil.createTestIslandDto();
		islandDto.setName("Cute Paradise Updated");
		String islandJson = objectMapper.writeValueAsString(islandDto);

		mockMvc.perform(
				MockMvcRequestBuilders.patch("/demo/islands/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(islandJson)
		).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void testThatPartialUpdateIslandUpdatesExistingIsland() throws Exception {
		Island island = TestDataUtil.createTestIslandA();
		Island savedIsland = islandService.createIsland(island);

		IslandDto islandDto = TestDataUtil.createTestIslandDto();
		islandDto.setId(savedIsland.getId());
		islandDto.setName("Cute Paradise Updated");
		String islandJson = objectMapper.writeValueAsString(islandDto);

		mockMvc.perform(
				MockMvcRequestBuilders.patch("/demo/islands/" + savedIsland.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(islandJson)
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.id").value(savedIsland.getId())
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.name").value(islandDto.getName())
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.owner").value(savedIsland.getOwner())
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.population").value(savedIsland.getPopulation())
		);

	}

	@Test
	public void testThatDeleteIslandReturnsHttpStatus204WhenIslandExists() throws Exception {
		Island island = TestDataUtil.createTestIslandA();
		islandService.createIsland(island);

		mockMvc.perform(
				MockMvcRequestBuilders.delete("/demo/islands/1")
						.contentType(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	public void testThatDeleteIslandReturnsHttpStatus404WhenNoIslandExist() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.delete("/demo/islands/1")
						.contentType(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

}
