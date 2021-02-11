package com.abinbev.admin.requestDto;

import java.util.Date;
import java.util.List;



import com.abinbev.admin.entity.Category;

import lombok.Data;
@Data
public class UserDto {
	
	private String uuid;
	private String firstName;
	private String lastName;
	private String emailId;
	private Long phoneNo;
	private List<String> roles;
	private List<CategoryDto> categories;
	private Boolean status;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	
	

}
