package com.abinbev.admin.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.abinbev.admin.requestDto.CategoryDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//import javax.validation.constraints.NotNull;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class User {
	@Id
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
