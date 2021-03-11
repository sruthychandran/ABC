package com.abinbev.admin.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abinbev.admin.config.MessageProperties;
import com.abinbev.admin.entity.CategoryServiceRoleMapping;
import com.abinbev.admin.requestDto.ProductAdminRoleMappingDto;
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.CategoryServiceRoleMappingResponseDto;
import com.abinbev.admin.responseDto.ErrorResponse;
import com.abinbev.admin.service.CategoryServiceRoleMappingService;
import com.abinbev.admin.service.ProductAdminService;
import com.abinbev.admin.utility.ErrorCodes;
import com.abinbev.admin.utility.MapperUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductAdminServiceImpl implements ProductAdminService {

	@Autowired
	CategoryServiceRoleMappingService categoryServiceRoleMappingService;

	@Autowired
	MessageProperties messageProperties;

	MapperUtil<ProductAdminRoleMappingDto, CategoryServiceRoleMapping> productAdminRoleMapper = new MapperUtil<>();

	MapperUtil<CategoryServiceRoleMapping, CategoryServiceRoleMappingResponseDto> productAdminRoleResponse = new MapperUtil<>();

	@Autowired
	private ErrorCodes errorCodes;

	@Override
	public BasicResponse<CategoryServiceRoleMappingResponseDto> saveRoleMapping(
			ProductAdminRoleMappingDto productAdminRoleMappingDto) {

		if (productAdminRoleMappingDto.getModules() != null && !productAdminRoleMappingDto.getModules().isEmpty()) {

			for (String moduleId : productAdminRoleMappingDto.getModules()) {

				

				CategoryServiceRoleMapping categoryServiceRoleMappingobj = categoryServiceRoleMappingService
						.findByModuleId(moduleId);
				if (categoryServiceRoleMappingobj == null) {
					CategoryServiceRoleMapping categoryRoleObj = new CategoryServiceRoleMapping();
					categoryRoleObj.setModuleId(moduleId);
					categoryRoleObj.setUserRoles(Arrays.asList("PA"));
					CategoryServiceRoleMapping result = categoryServiceRoleMappingService.save(categoryRoleObj);

				} else {
					List<String> userRoleList = categoryServiceRoleMappingobj.getUserRoles();
					userRoleList.add("PA");
					categoryServiceRoleMappingobj.setUserRoles(userRoleList);
					
					  CategoryServiceRoleMapping result = categoryServiceRoleMappingService
					  .save(categoryServiceRoleMappingobj);
					 

				}
				

			}
		}

		BasicResponse<CategoryServiceRoleMappingResponseDto> basicResponse = new BasicResponse<CategoryServiceRoleMappingResponseDto>();
		ErrorResponse error = new ErrorResponse(null, null);
		basicResponse.setError(error);
		basicResponse.setMessage(messageProperties.getCategoryServiceRoleMappingSaveSuccessMessage());
		basicResponse.setCode(messageProperties.getCategoryServiceRoleMappingSaveSuccesCode());

		// basicResponse.setData(null);
		return basicResponse;

	}

}
