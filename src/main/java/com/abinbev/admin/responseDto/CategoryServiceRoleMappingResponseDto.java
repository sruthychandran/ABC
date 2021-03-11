package com.abinbev.admin.responseDto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryServiceRoleMappingResponseDto {

	private String categoryId;

	private List<String> userRoles;
	private String moduleId;
}
