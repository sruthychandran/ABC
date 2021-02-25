package com.abinbev.admin.entity;


import org.springframework.data.annotation.Id;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.abinbev.admin.entity.User.UserBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Role {
	@Id
	private String id;
	private String roleId;
	private String roleName;
	private String roleDescription;
	private List<String> platformServices;
	private List<String> coreServices;
	private String status;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private String userRole;

}
