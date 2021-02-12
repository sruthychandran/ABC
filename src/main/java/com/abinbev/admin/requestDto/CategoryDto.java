package com.abinbev.admin.requestDto;

import java.util.Date;
import java.util.List;

import com.abinbev.admin.entity.User;
import com.abinbev.admin.entity.User.UserBuilder;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {
	private String categoryId;
	private List<String> moduleId;

}
