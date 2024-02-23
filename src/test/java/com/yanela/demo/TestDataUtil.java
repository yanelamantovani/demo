package com.yanela.demo;

import com.yanela.demo.domain.dto.AnimalDto;
import com.yanela.demo.domain.dto.IslandDto;
import com.yanela.demo.domain.entity.Animal;
import com.yanela.demo.domain.entity.Island;

public final class TestDataUtil {

	public TestDataUtil() {
	}

	public static Island createTestIslandA() {
		return Island.builder()
				.id(1L)
				.name("Cute Paradise")
				.owner("Yanela")
				.population(12)
				.build();
	}

	public static Island createTestIslandB() {
		return Island.builder()
				.id(2L)
				.name("Kawaii Land")
				.owner("Luigi")
				.population(8)
				.build();
	}

	public static IslandDto createTestIslandDto() {
		return IslandDto.builder()
				.id(1L)
				.name("Cute Paradise")
				.owner("Yanela")
				.population(12)
				.build();
	}

	public static Animal createTestAnimalA(final Island island) {
		return Animal.builder()
				.id(1L)
				.name("Tom Nook")
				.island(island)
				.build();
	}

	public static Animal createTestAnimalB(final Island island) {
		return Animal.builder()
				.id(2L)
				.name("Canela")
				.island(island)
				.build();
	}

	public static AnimalDto createTestAnimalDto(final IslandDto island) {
		return AnimalDto.builder()
				.id(1L)
				.name("Tom Nook")
				.island(island)
				.build();
	}

}
