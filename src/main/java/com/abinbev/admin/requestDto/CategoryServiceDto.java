package com.abinbev.admin.requestDto;



import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.abinbev.admin.requestDto.UserDto.UserDtoBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryServiceDto {
	private String id;
	private String categoryId;
	private String categoryName;
	private String moduleId;
	private String moduleName;
	private String subModuleId;
	private String subModuleName;
	private String status;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private String userRole;
}
