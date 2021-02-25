package com.abinbev.admin.requestDto;

import java.util.Date;
import java.util.List;

import com.abinbev.admin.entity.User;
import com.abinbev.admin.entity.User.UserBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestCategoryDto {
	private String categoryId;
	private String categoryName;
	private List<TestModuleDto> subModules;

}
