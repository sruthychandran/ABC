package com.abinbev.admin.requestDto;

import java.util.Date;
import java.util.List;

import com.abinbev.admin.requestDto.UserDto.UserDtoBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryServiceRoleMappingDto {
	
	private String categoryId;
	
	private List<String> userRoles;
	
}
