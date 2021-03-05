package com.abinbev.admin.responseDto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionResponseDto {

	private String id;
	private String permissionId;
	private String permissionName;
	private String permissionDescription;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private String status;
	private String roleId;


}
