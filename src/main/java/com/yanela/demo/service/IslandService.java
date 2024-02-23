package com.yanela.demo.service;

import com.yanela.demo.domain.entity.Island;

import java.util.List;
import java.util.Optional;

public interface IslandService {

	List<Island> getIslands();

	Optional<Island> getIsland(Long id);

	Island createIsland(Island island);

	boolean islandExists(Long id);

	Island updateIsland(Long id, Island island);

	void deleteIsland(Long id);

}
