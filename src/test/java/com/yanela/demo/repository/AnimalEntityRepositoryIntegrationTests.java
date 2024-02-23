package com.yanela.demo.repository;

import com.yanela.demo.TestDataUtil;
import com.yanela.demo.domain.entity.Animal;
import com.yanela.demo.domain.entity.Island;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AnimalEntityRepositoryIntegrationTests {

	@Autowired
	private AnimalRepository underTest;

	@Test
	public void testThatAnimalCanBeCreatedAndRecalled() {
		Island island = TestDataUtil.createTestIslandA();
		Animal animal = TestDataUtil.createTestAnimalA(island);
		underTest.save(animal);

		Optional<Animal> result = underTest.findById(animal.getId());
		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(animal);
	}

	@Test
	public void testThatMultipleAnimalsCanBeCreatedAndRecalled() {
		Island island = TestDataUtil.createTestIslandA();
		Animal animalA = TestDataUtil.createTestAnimalA(island);
		underTest.save(animalA);
		Animal animalB = TestDataUtil.createTestAnimalB(island);
		underTest.save(animalB);

		Iterable<Animal> result = underTest.findAll();
		assertThat(result)
				.hasSize(2)
				.containsExactly(animalA, animalB);
	}

	@Test
	public void testThatAnimalCanBeUpdated() {
		Island island = TestDataUtil.createTestIslandA();
		Animal animal = TestDataUtil.createTestAnimalA(island);
		underTest.save(animal);

		animal.setName("Updated");
		underTest.save(animal);

		Optional<Animal> result = underTest.findById(animal.getId());
		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(animal);
	}

	@Test
	public void testThatAnimalCanBeDeleted() {
		Island island = TestDataUtil.createTestIslandA();
		Animal animal = TestDataUtil.createTestAnimalA(island);
		underTest.save(animal);

		underTest.delete(animal);

		Optional<Animal> result = underTest.findById(animal.getId());
		assertThat(result).isEmpty();
	}

}
