package com.abinbev.admin.responseDto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryServiceResponseDto {
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
