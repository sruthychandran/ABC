package com.abinbev.admin.responseDto;

import java.util.Date;

import lombok.Data;

@Data
public class RoleResponseDto {
	private String roleId;
	private String roleName;
	private Boolean status;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private String message;
}
