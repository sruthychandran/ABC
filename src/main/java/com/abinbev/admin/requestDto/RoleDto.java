package com.abinbev.admin.requestDto;

import java.util.Date;
import java.util.List;

import com.abinbev.admin.requestDto.UserDto.UserDtoBuilder;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleDto {
	private String roleId;
	private String roleName;
	private String status;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private String userRole;
	
}
