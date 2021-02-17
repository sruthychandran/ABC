package com.abinbev.admin.responseDto;

import java.util.Date;
import java.util.List;

import com.abinbev.admin.requestDto.PlatformAdminCategoryDto;

import lombok.Data;

@Data
public class PlatformAdminOnBoardingResponseDto {

	public String id;
	private String firstName;
	private String lastName;
	private String emailId;
	private Long phoneNo;
	private String isPlatformSuperAdmin;
	private List<PlatformAdminCategoryDto> categories;
	private String status;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private String message;
}
