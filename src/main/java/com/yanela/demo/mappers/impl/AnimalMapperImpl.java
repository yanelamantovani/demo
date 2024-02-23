package com.yanela.demo.mappers.impl;

import com.yanela.demo.domain.dto.AnimalDto;
import com.yanela.demo.domain.entity.Animal;
import com.yanela.demo.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnimalMapperImpl implements Mapper<Animal, AnimalDto> {

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public AnimalDto mapTo(Animal animal) {
		return modelMapper.map(animal, AnimalDto.class);
	}

	@Override
	public Animal mapFrom(AnimalDto animalDto) {
		return modelMapper.map(animalDto, Animal.class);
	}

}
