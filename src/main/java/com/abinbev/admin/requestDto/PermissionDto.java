package com.abinbev.admin.requestDto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDto {

	private String id;
	private String permissionId;
	private String permissionName;
	private String permissionDescription;
	private String roleId;
	private String message;


}
