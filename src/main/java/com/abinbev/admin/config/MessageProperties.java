package com.abinbev.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Data
@Configuration
@PropertySource("classpath:message.properties")
public class MessageProperties {

	/*
	 * @Value("${message.create}") String saveMessage;
	 * 
	 * @Value("${message.update}") String updationMessage;
	 */
	/*
	 * @Value("${message.delete}") String deletionMessage;
	 */
	@Value("${user.status.active}")
	String activeStatus;
	@Value("${user.status.inactive}")
	String inactiveStatus;
	/*
	 * @Value("${user.save.failure}") String userSaveFailureMessage;
	 * 
	 * @Value("${user.update.failure}") String userUpdateFailureMessage;
	 * 
	 * @Value("${user.email.exist}") String userEmailExistMessage;
	 * 
	 * @Value("${user.notfound}") String userNotfoundMessage;
	 * 
	 * @Value("${role.notfound}") String roleNotfoundMessage;
	 * 
	 * @Value("${role.save.failure}") String roleSaveFailureMessage;
	 * 
	 * @Value("${role.update.failure}") String roleUpdateFailureMessage;
	 * 
	 * @Value("${permission.notfound}") String permissionNotfoundMessage;
	 * 
	 * @Value("${permission.save.failure}") String permissionSaveFailureMessage;
	 * 
	 * @Value("${permission.update.failure}") String permissionUpdateFailureMessage;
	 * 
	 * @Value("${categoryService.save.failure}") String
	 * categoryServiceSaveFailureMessage;
	 * 
	 * @Value("${categoryService.update.failure}") String
	 * categoryServiceUpdateFailureMessage;
	 * 
	 * @Value("${categoryService.notfound}") String CategoryServiceNotFoundMessage;
	 */

	@Value("${categoryService.save.successMessage}")
	String CatergoryServiceSaveSuccessMessage;
	@Value("${categoryService.save.successCode}")
	String CategoryServiceSaveSuccesCode;
	@Value("${categoryService.update.successMessage}")
	String CatergoryServiceUpdateSuccessMessage;
	@Value("${categoryService.update.successCode}")
	String CategoryServiceUpdateSuccesCode;
	@Value("${categoryService.delete.successMessage}")
	String CatergoryServiceDeleteSuccessMessage;
	@Value("${categoryService.delete.successCode}")
	String CategoryServiceDeleteSuccesCode;

	@Value("${categoryService.retrieve.successMessage}")
	String CatergoryServiceRetrieveSuccessMessage;
	@Value("${categoryService.retrieve.successCode}")
	String CategoryServiceRetrieveSuccesCode;
	
	
	
	@Value("${role.save.successMessage}")
	String roleSaveSuccessMessage;
	@Value("${role.save.successCode}")
	String roleSaveSuccesCode;
	@Value("${role.update.successMessage}")
	String roleUpdateSuccessMessage;
	@Value("${role.update.successCode}")
	String roleUpdateSuccesCode;
	@Value("${role.delete.successMessage}")
	String roleDeleteSuccessMessage;
	@Value("${role.delete.successCode}")
	String roleDeleteSuccesCode;

	@Value("${role.retrieve.successMessage}")
	String roleRetrieveSuccessMessage;
	@Value("${role.retrieve.successCode}")
	String roleRetrieveSuccesCode;
	
	
	@Value("${platformAdmin.save.successMessage}")
	String platformAdminSaveSuccessMessage;
	@Value("${platformAdmin.save.successCode}")
	String platformAdminSaveSuccesCode;
	@Value("${platformAdmin.update.successMessage}")
	String platformAdminUpdateSuccessMessage;
	@Value("${platformAdmin.update.successCode}")
	String platformAdminUpdateSuccesCode;
	@Value("${platformAdmin.delete.successMessage}")
	String platformAdminDeleteSuccessMessage;
	@Value("${platformAdmin.delete.successCode}")
	String platformAdminDeleteSuccesCode;

	@Value("${platformAdmin.retrieve.successMessage}")
	String platformAdminRetrieveSuccessMessage;
	@Value("${platformAdmin.retrieve.successCode}")
	String platformAdminRetrieveSuccesCode;
	
	
	@Value("${tenantAdmin.save.successMessage}")
	String tenantAdminSaveSuccessMessage;
	@Value("${tenantAdmin.save.successCode}")
	String tenantAdminSaveSuccesCode;
	@Value("${tenantAdmin.update.successMessage}")
	String tenantAdminUpdateSuccessMessage;
	@Value("${tenantAdmin.update.successCode}")
	String tenantAdminUpdateSuccesCode;
	@Value("${tenantAdmin.delete.successMessage}")
	String tenantAdminDeleteSuccessMessage;
	@Value("${tenantAdmin.delete.successCode}")
	String tenantAdminDeleteSuccesCode;

	@Value("${tenantAdmin.retrieve.successMessage}")
	String tenantAdminRetrieveSuccessMessage;
	@Value("${tenantAdmin.retrieve.successCode}")
	String tenantAdminRetrieveSuccesCode;
	
	
	
	
	@Value("${permission.save.successMessage}")
	String permissionSaveSuccessMessage;
	@Value("${permission.save.successCode}")
	String permissionSaveSuccesCode;
	@Value("${permission.update.successMessage}")
	String permissionUpdateSuccessMessage;
	@Value("${permission.update.successCode}")
	String permissionUpdateSuccesCode;
	@Value("${permission.delete.successMessage}")
	String permissionDeleteSuccessMessage;
	@Value("${permission.delete.successCode}")
	String permissionDeleteSuccesCode;

	@Value("${permission.retrieve.successMessage}")
	String permissionRetrieveSuccessMessage;
	@Value("${permission.retrieve.successCode}")
	String permissionRetrieveSuccesCode;
	
	
	
	@Value("${nocontent.errorCode}")
	String noContentErrorCode;
	@Value("${nocontent.errorMessage}")
	String noContentErrorMessage;
	
	

}
