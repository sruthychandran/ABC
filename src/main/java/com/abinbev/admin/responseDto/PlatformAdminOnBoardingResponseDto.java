package com.abinbev.admin.responseDto;

import java.util.Date;
import java.util.List;

import com.abinbev.admin.entity.CategoryDetails;
import com.abinbev.admin.requestDto.PlatformAdminCategoryDto;
import com.abinbev.admin.responseDto.UserResponseDto.UserResponseDtoBuilder;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlatformAdminOnBoardingResponseDto {

	public String id;
	private String firstName;
	private String lastName;
	private String emailId;
	private Long phoneNo;
	private String isPlatformSuperAdmin;
	private List<PlatformAdminCategoryDto> categories;
	private String status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date createdDate;
	private String createdBy;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date modifiedDate;
	private String modifiedBy;
	
}
