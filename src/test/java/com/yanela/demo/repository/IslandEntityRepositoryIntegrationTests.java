package com.yanela.demo.repository;

import com.yanela.demo.TestDataUtil;
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
public class IslandEntityRepositoryIntegrationTests {

	@Autowired
	private IslandRepository underTest;

	@Test
	public void testThatIslandCanBeCreatedAndRecalled() {
		Island island = TestDataUtil.createTestIslandA();
		underTest.save(island);

		Optional<Island> result = underTest.findById(island.getId());
		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(island);
	}

	@Test
	public void testThatMultipleIslandsCanBeCreatedAndRecalled() {
		Island islandA = TestDataUtil.createTestIslandA();
		underTest.save(islandA);
		Island islandB = TestDataUtil.createTestIslandB();
		underTest.save(islandB);

		Iterable<Island> result = underTest.findAll();
		assertThat(result)
				.hasSize(2)
				.containsExactly(islandA, islandB);
	}

	@Test
	public void testThatIslandCanBeUpdated() {
		Island island = TestDataUtil.createTestIslandA();
		underTest.save(island);

		island.setName("Updated");
		underTest.save(island);

		Optional<Island> result = underTest.findById(island.getId());
		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(island);
	}

	@Test
	public void testThatIslandCanBeDeleted() {
		Island island = TestDataUtil.createTestIslandA();
		underTest.save(island);

		underTest.delete(island);

		Optional<Island> result = underTest.findById(island.getId());
		assertThat(result).isEmpty();
	}

	@Test
	public void testThatGetIslandsWithPopulationLessThan() {
		Island islandA = TestDataUtil.createTestIslandA();
		underTest.save(islandA);
		Island islandB = TestDataUtil.createTestIslandB();
		underTest.save(islandB);

		Iterable<Island> result = underTest.populationLessThan(10);
		assertThat(result).containsExactly(islandB);
	}

	@Test
	public void testThatGetIslandsWithPopulationGreaterThan() {
		Island islandA = TestDataUtil.createTestIslandA();
		underTest.save(islandA);
		Island islandB = TestDataUtil.createTestIslandB();
		underTest.save(islandB);

		Iterable<Island> result = underTest.findIslandsWithPopulationGreaterThan(10);
		assertThat(result).containsExactly(islandA);
	}

}
