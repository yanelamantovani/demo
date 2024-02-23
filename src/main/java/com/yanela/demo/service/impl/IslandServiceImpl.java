package com.yanela.demo.service.impl;

import com.yanela.demo.domain.entity.Island;
import com.yanela.demo.repository.IslandRepository;
import com.yanela.demo.service.IslandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class IslandServiceImpl implements IslandService {

	@Autowired
	private IslandRepository islandRepository;

	@Override
	public List<Island> getIslands() {
		return StreamSupport.stream(islandRepository
					.findAll()
					.spliterator(),
					false)
				.toList();
	}

	@Override
	public Optional<Island> getIsland(Long id) {
		return islandRepository.findById(id);
	}

	@Override
	public Island createIsland(Island island) {
		return islandRepository.save(island);
	}

	@Override
	public boolean islandExists(Long id) {
		return islandRepository.existsById(id);
	}

	@Override
	public Island updateIsland(Long id, Island island) {
		island.setId(id);

		return islandRepository.findById(id).map(existingIsland -> {
			Optional.ofNullable(island.getName()).ifPresent(existingIsland::setName);
			Optional.ofNullable(island.getOwner()).ifPresent(existingIsland::setOwner);
			Optional.ofNullable(island.getPopulation()).ifPresent(existingIsland::setPopulation);
			return islandRepository.save(existingIsland);
		}).orElseThrow(()-> new RuntimeException("Island does not exist"));
	}

	@Override
	public void deleteIsland(Long id) {
		islandRepository.deleteById(id);
	}

}
