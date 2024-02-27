package com.yanela.demo.controller;

import com.yanela.demo.domain.dto.IslandDto;
import com.yanela.demo.domain.entity.Island;
import com.yanela.demo.mappers.Mapper;
import com.yanela.demo.service.IslandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/demo/islands")
@Tag(name = "Islands")
public class IslandController {

	@Autowired
	private IslandService islandService;

	@Autowired
	private Mapper<Island, IslandDto> islandMapper;

	@Operation(
			description = "Get all islands",
			summary = "Get all islands",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK"
					)
			}
	)
	@GetMapping
	public List<IslandDto> getIslands() {
		List<Island> islands = islandService.getIslands();
		return islands.stream()
				.map(islandMapper::mapTo)
				.toList();
	}

	@Operation(
			description = "Get island by ID",
			summary = "Get island by ID",
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
	public ResponseEntity<IslandDto> getIsland(@PathVariable("id") Long id) {
		Optional<Island> foundIsland = islandService.getIsland(id);
		return foundIsland.map(island -> {
			IslandDto islandDto = islandMapper.mapTo(island);
			return new ResponseEntity<>(islandDto,HttpStatus.OK);
		}).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@Operation(
			description = "Create new island",
			summary = "Create new island",
			responses = {
					@ApiResponse(
							responseCode = "201",
							description = "Created"
					)
			}
	)
	@PostMapping
	public ResponseEntity<IslandDto> createIsland(@RequestBody IslandDto islandDto) {
		Island island = islandMapper.mapFrom(islandDto);
		Island savedIsland = islandService.createIsland(island);
		return new ResponseEntity<>(
				islandMapper.mapTo(savedIsland),
				HttpStatus.CREATED);
	}

	@Operation(
			description = "Update an island by ID",
			summary = "Update an island by ID",
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
	public ResponseEntity<IslandDto> fullUpdateIsland(
			@PathVariable("id") Long id,
			@RequestBody IslandDto islandDto) {
		if (!islandService.islandExists(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		islandDto.setId(id);
		Island island = islandMapper.mapFrom(islandDto);
		Island savedIsland = islandService.createIsland(island);
		return new ResponseEntity<>(
				islandMapper.mapTo(savedIsland),
				HttpStatus.OK);
	}

	@Operation(
			description = "Update some attributes of an island by ID",
			summary = "Update some attributes of an island by ID",
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
	public ResponseEntity<IslandDto> partialUpdateIsland(
			@PathVariable("id") Long id,
			@RequestBody IslandDto islandDto) {
		if (!islandService.islandExists(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Island island = islandMapper.mapFrom(islandDto);
		Island updatedIsland = islandService.updateIsland(id, island);
		return new ResponseEntity<>(
				islandMapper.mapTo(updatedIsland),
				HttpStatus.OK);
	}

	@Operation(
			description = "Delete island by ID",
			summary = "Delete island by ID",
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
	public ResponseEntity deleteIsland(@PathVariable("id") Long id) {
		if (!islandService.islandExists(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		islandService.deleteIsland(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
