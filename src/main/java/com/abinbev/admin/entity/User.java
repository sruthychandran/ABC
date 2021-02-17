package com.abinbev.admin.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.abinbev.admin.requestDto.CategoryDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class User {
	@Id
	private String id;
	private String emailId;
	private String firstName;
	private String lastName;
	private Long phoneNo;
	private String roleId;
	private List<CategoryDto> categories;
	private String status;
	private String isPlatformSuperAdmin;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;

}
