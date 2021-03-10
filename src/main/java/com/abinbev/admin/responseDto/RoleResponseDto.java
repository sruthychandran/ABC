package com.abinbev.admin.responseDto;

import java.util.Date;
import java.util.List;

import com.abinbev.admin.entity.CategoryDetails;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponseDto {
	private String id;
	private String roleId;
	private String roleName;
	private String roleDescription;
	private List<String> platformServices;
	private List<String> coreServices;
	private String status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date createdDate;
	private String createdBy;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date modifiedDate;
	private String modifiedBy;
	private String userRole;
}
