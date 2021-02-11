package com.abinbev.admin.requestDto;



import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
public class CategoryServiceDto {
	private String categoryId;
	private String categoryName;
	private String moduleId;
	private String moduleName;
	private Boolean status;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;

}
