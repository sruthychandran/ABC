package com.abinbev.admin.requestDto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlatformAdminOnBoardingDto {
	public String id;
	@NotNull(message = "first name is mandatory")
	private String firstName;
	private String lastName;
	@NotNull(message = "email is mandatory")
	private String emailId;
	private Long phoneNo;
	private String isPlatformSuperAdmin;
	private List<PlatformAdminCategoryDto> categories;
	private String status;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;

}
