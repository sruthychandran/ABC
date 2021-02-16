package com.abinbev.admin.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
public class TenantAdminServiceTests {

	@Autowired
	private TenantAdminService tenantAdminService;

	private MockMvc mockMvc;

	@Test
	public void getAllUsers_shouldNotReturnNull() throws Exception {
	    // assertThat is imported from Assert; notNullValue is imported from Matchers
	  //  assertThat(tenantAdminService.getAllUsers(), notNullValue());
	}

	public TenantAdminService getTenantAdminService() {
		return tenantAdminService;
	}

	public void setTenantAdminService(TenantAdminService tenantAdminService) {
		this.tenantAdminService = tenantAdminService;
	}

	public MockMvc getMockMvc() {
		return mockMvc;
	}

	public void setMockMvc(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}
	
	
	
	
	
	  
	
}