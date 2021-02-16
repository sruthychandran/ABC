package com.abinbev.admin.requestDto;

import java.util.Date;
import java.util.List;



import com.abinbev.admin.entity.Category;
import com.abinbev.admin.entity.User;
import com.abinbev.admin.entity.User.UserBuilder;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
	
public class UserDto {
	
	private String uuid;
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
	
	

}
