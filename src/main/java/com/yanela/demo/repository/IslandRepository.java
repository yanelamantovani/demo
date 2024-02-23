package com.yanela.demo.repository;

import com.yanela.demo.domain.entity.Island;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IslandRepository extends JpaRepository<Island, Long> {

	Iterable<Island> populationLessThan(int population);

	@Query("SELECT a from Island a where a.population > ?1")
	Iterable<Island> findIslandsWithPopulationGreaterThan(int population);

}
