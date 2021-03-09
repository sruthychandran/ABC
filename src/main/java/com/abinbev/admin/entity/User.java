package com.abinbev.admin.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
	private String password;
	private Long phoneNo;
	private String roleId;
	private List<CategoryDetails> categories;
	private String status;
	private String isPlatformSuperAdmin;
	private String isProductSuperAdmin;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;

}
