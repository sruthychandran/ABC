package com.abinbev.admin.entity;

import java.util.List;

import lombok.Data;

@Data
public class Category {
	private String categoryId;
	private List<String> moduleId;

}
