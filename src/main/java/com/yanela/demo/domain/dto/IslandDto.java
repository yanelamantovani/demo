package com.yanela.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IslandDto {

	private Long id;

	private String name;

	private String owner;

	private Integer population;

}
