package com.abinbev.admin.responseDto;

import java.util.Date;
import java.util.List;

import com.abinbev.admin.entity.Category;
import com.abinbev.admin.requestDto.CategoryDto;
import com.abinbev.admin.requestDto.UserDto;
import com.abinbev.admin.responseDto.RoleResponseDto.RoleResponseDtoBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
//	private String uuid;
	private String firstName;
	private String lastName;
	private String emailId;
	private Long phoneNo;
	private String roleId;
	private List<CategoryDto> categories;
	private String status;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private String message;
}
