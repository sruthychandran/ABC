package com.abinbev.admin.responseDto;

import java.util.Date;
import java.util.List;

import com.abinbev.admin.requestDto.CategoryDto;
import com.abinbev.admin.responseDto.UserResponseDto.UserResponseDtoBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * error response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ErrorResponse {

	private String errorCode;
	private String errorMessage;
	


}