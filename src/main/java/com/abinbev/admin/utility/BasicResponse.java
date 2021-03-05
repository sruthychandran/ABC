package com.abinbev.admin.utility;

import java.util.Date;
import java.util.List;

import com.abinbev.admin.requestDto.CategoryDto;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * basic response
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BasicResponse<T> {

	private String projectStatusCode;
	private String message;
	private T response;

	/*
	 * public static <T> BasicResponse<T> create(String code, T t) { return new
	 * BasicResponse<>(code, t); }
	 */

	/*
	 * public static <T> BasicResponse<T> create(ProjectSuccessStatusCode
	 * projectSuccessStatusCode) { return create(projectSuccessStatusCode, null); }
	 */

}