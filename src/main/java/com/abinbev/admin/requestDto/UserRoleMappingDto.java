package com.abinbev.admin.requestDto;

import java.util.Date;
import java.util.List;

import com.abinbev.admin.requestDto.UserDto.UserDtoBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleMappingDto {
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
	private List<String> userRoles;
	
}
