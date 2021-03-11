package com.abinbev.admin.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abinbev.admin.requestDto.CategoryServiceRoleMappingDto;
import com.abinbev.admin.requestDto.ProductAdminRoleMappingDto;
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.CategoryServiceRoleMappingResponseDto;
import com.abinbev.admin.service.ProductAdminService;
import com.abinbev.admin.utility.ErrorCodes;

@RestController
@RequestMapping("/productAdminController/v1")
public class ProductAdminController {

	private static final Logger log = LoggerFactory.getLogger(ProductAdminController.class);

	@Autowired
	ProductAdminService productAdminService;

	@Autowired
	private ErrorCodes errorCodes;

	/**
	 * In this method a create permission for productAdmin
	 * 
	 * @param CategoryServiceRoleMappingDto
	 * @return CategoryServiceRoleMappingResponseDto
	 * 
	 */

	@PostMapping("/productAdmin/permission")
	public ResponseEntity<BasicResponse<CategoryServiceRoleMappingResponseDto>> createCategoryRoleMapping(
			@Valid @RequestBody ProductAdminRoleMappingDto productAdminRoleMappingDto) {

		
		BasicResponse<CategoryServiceRoleMappingResponseDto> result = productAdminService
				.saveRoleMapping(productAdminRoleMappingDto);

		return ResponseEntity.ok().body(result);

	}

}
