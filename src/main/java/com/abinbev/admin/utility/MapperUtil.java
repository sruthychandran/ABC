package com.abinbev.admin.utility;

import org.modelmapper.ModelMapper;

public class MapperUtil<S,D> {
	
	public D transfer (S s, Class<D> clazz) {
		return new ModelMapper().map(s, clazz);
	}
	
}
