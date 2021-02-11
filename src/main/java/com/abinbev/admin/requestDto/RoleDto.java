package com.abinbev.admin.requestDto;

import java.util.Date;

import lombok.Data;

@Data
public class RoleDto {
	private String roleId;
	private String roleName;
	private Boolean status;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	
}
