package com.abinbev.admin.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 *  SucessResponse
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class SuccessResponse {
	private String messageDescription;
	private String messageCode;

}