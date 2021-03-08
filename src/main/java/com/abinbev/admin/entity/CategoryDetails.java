package com.abinbev.admin.entity;

import java.util.Date;
import java.util.List;

import com.abinbev.admin.entity.User.UserBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDetails {
	private String categoryId;
	private List<ModuleDetails> modules;

}
