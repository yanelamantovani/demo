package com.yanela.demo.repository;

import com.yanela.demo.domain.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long>, PagingAndSortingRepository<Animal, Long> {

}
