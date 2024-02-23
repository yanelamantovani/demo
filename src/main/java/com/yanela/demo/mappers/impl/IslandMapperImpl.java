package com.yanela.demo.mappers.impl;

import com.yanela.demo.domain.dto.IslandDto;
import com.yanela.demo.domain.entity.Island;
import com.yanela.demo.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IslandMapperImpl implements Mapper<Island, IslandDto> {

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public IslandDto mapTo(Island island) {
		return modelMapper.map(island, IslandDto.class);
	}

	@Override
	public Island mapFrom(IslandDto islandDto) {
		return modelMapper.map(islandDto, Island.class);
	}

}
