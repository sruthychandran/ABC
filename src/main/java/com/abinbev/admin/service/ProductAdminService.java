package com.abinbev.admin.service;

import com.abinbev.admin.requestDto.ProductAdminRoleMappingDto;
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.CategoryServiceRoleMappingResponseDto;

public interface ProductAdminService {

	public BasicResponse<CategoryServiceRoleMappingResponseDto> saveRoleMapping(
			ProductAdminRoleMappingDto productAdminRoleMappingDto);

}
