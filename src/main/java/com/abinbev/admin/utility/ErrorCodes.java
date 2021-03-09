package com.abinbev.admin.utility;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class ErrorCodes {
  
  



  
  @NotNull
  @Valid
  @JsonProperty("role_not_found")
  private ErrorCodeMessage roleNotFound;

  @NotNull
  @Valid
  @JsonProperty("user_not_found")
  private ErrorCodeMessage userNotFound;
  
  @NotNull
  @Valid
  @JsonProperty("permission_not_found")
  private ErrorCodeMessage permissionNotFound;
  
  @NotNull
  @Valid
  @JsonProperty("category_service_not_found")
  private ErrorCodeMessage categoryServiceNotFound;
  
  @NotNull
  @Valid
  @JsonProperty("role_save_failure")
  private ErrorCodeMessage roleSaveFailure;

  @NotNull
  @Valid
  @JsonProperty("user_save_failure")
  private ErrorCodeMessage userSaveFailure;
  
  @NotNull
  @Valid
  @JsonProperty("permission_save_failure")
  private ErrorCodeMessage permissionSaveFailure;
  
  @NotNull
  @Valid
  @JsonProperty("category_service_save_failure")
  private ErrorCodeMessage categoryServiceSaveFailure;

  @NotNull
  @Valid
  @JsonProperty("role_update_failure")
  private ErrorCodeMessage roleUpdateFailure;

  @NotNull
  @Valid
  @JsonProperty("user_update_failure")
  private ErrorCodeMessage userUpdateFailure;
  
  @NotNull
  @Valid
  @JsonProperty("permission_update_failure")
  private ErrorCodeMessage permissionUpdateFailure;
  
  @NotNull
  @Valid
  @JsonProperty("category_service_update_failure")
  private ErrorCodeMessage categoryServiceUpdateFailure;


  @NotNull
  @Valid
  @JsonProperty("email_exist")
  private ErrorCodeMessage emailExist;


  @NotNull
  @Valid
  @JsonProperty("invalid_id")
  private ErrorCodeMessage invalidId;

  
  @NotNull
  @Valid
  @JsonProperty("invalid_permission_id")
  private ErrorCodeMessage invalidPermissionId;

  @NotNull
  @Valid
  @JsonProperty("invalid_role_id")
  private ErrorCodeMessage invalidRoleId;
  
  @NotNull
  @Valid
  @JsonProperty("invalid_email_id")
  private ErrorCodeMessage invalidEmailId;
  
  @NotNull
  @Valid
  @JsonProperty("invalid_category_service_id")
  private ErrorCodeMessage invalidCategoryServiceId;
  
  @NotNull
  @Valid
  @JsonProperty("user_exist")
  private ErrorCodeMessage userExist;
  
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ErrorCodeMessage {

    @NotEmpty
    @JsonProperty("error_code")
    private String errorCode;

    @NotEmpty
    @JsonProperty("error_message")
    private String errorMessage;
  }

}
