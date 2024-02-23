package com.yanela.demo.controller;

import com.yanela.demo.domain.dto.IslandDto;
import com.yanela.demo.domain.entity.Island;
import com.yanela.demo.mappers.Mapper;
import com.yanela.demo.service.IslandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class IslandController {

	@Autowired
	private IslandService islandService;

	@Autowired
	private Mapper<Island, IslandDto> islandMapper;

	@GetMapping(path = "/islands")
	public List<IslandDto> getIslands() {
		List<Island> islands = islandService.getIslands();
		return islands.stream()
				.map(islandMapper::mapTo)
				.toList();
	}

	@GetMapping(path = "/islands/{id}")
	public ResponseEntity<IslandDto> getIsland(@PathVariable("id") Long id) {
		Optional<Island> foundIsland = islandService.getIsland(id);
		return foundIsland.map(island -> {
			IslandDto islandDto = islandMapper.mapTo(island);
			return new ResponseEntity<>(islandDto,HttpStatus.OK);
		}).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping(path = "/islands")
	public ResponseEntity<IslandDto> createIsland(@RequestBody IslandDto islandDto) {
		Island island = islandMapper.mapFrom(islandDto);
		Island savedIsland = islandService.createIsland(island);
		return new ResponseEntity<>(
				islandMapper.mapTo(savedIsland),
				HttpStatus.CREATED);
	}

	@PutMapping(path = "/islands/{id}")
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

	@PatchMapping(path = "/islands/{id}")
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

	@DeleteMapping(path = "/islands/{id}")
	public ResponseEntity deleteIsland(@PathVariable("id") Long id) {
		if (!islandService.islandExists(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		islandService.deleteIsland(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
