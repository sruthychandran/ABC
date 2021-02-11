package com.abinbev.admin.entity;



import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.abinbev.admin.entity.User.UserBuilder;
import com.abinbev.admin.requestDto.CategoryServiceDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class CategoryService {
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
