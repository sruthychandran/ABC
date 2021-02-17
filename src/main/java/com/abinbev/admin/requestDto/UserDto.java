package com.abinbev.admin.requestDto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class UserDto {
	public String id;
	@NotNull(message = "first name is mandatory")
	private String firstName;
	private String lastName;
	@NotNull(message = "email is mandatory")
	private String emailId;
	private Long phoneNo;
	private String roleId;
	private List<CategoryDto> categories;
	private String status;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;

}
