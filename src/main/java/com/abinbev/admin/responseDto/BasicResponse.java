package com.abinbev.admin.responseDto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * basic response
 */
@Data
@Getter
@Setter

@NoArgsConstructor
@ToString
public class BasicResponse<T>{

	private ErrorResponse error;
	private String code;
	private String message;
	private T data;


}