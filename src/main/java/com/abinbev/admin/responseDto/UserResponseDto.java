package com.abinbev.admin.responseDto;

import java.util.Date;
import java.util.List;

import com.abinbev.admin.entity.Category;
import com.abinbev.admin.requestDto.UserDto;

import lombok.Data;
@Data
public class UserResponseDto {
	private String uuid;
	private String firstName;
	private String lastName;
	private String emailId;
	private Long phoneNo;
	private String roleId;
	private List<Category> category;
	private Boolean status;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private String message;
}
