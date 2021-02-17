package com.abinbev.admin.requestDto;



import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.abinbev.admin.requestDto.UserDto.UserDtoBuilder;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryServiceDto {
	private String categoryId;
	private String categoryName;
	private String moduleId;
	private String moduleName;
	private String status;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private String userRole;
}