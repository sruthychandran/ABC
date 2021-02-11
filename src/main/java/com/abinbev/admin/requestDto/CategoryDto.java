package com.abinbev.admin.requestDto;

import java.util.List;

import lombok.Data;

@Data
public class CategoryDto {
	private String categoryId;
	private List<String> moduleId;

}
