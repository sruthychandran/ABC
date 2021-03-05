package com.abinbev.admin.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abinbev.admin.dao.UserDAO;
import com.abinbev.admin.entity.User;
import com.abinbev.admin.requestDto.LoginDto;
import com.abinbev.admin.requestDto.SignupDto;
import com.abinbev.admin.service.EmailService;
import com.abinbev.admin.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	
	@Autowired
	EmailService emailService;
	
	@Autowired
	UserDAO userDAO;

	@Override
	public void login(LoginDto loginDto) {
		
		User user=userDAO.findByEmail(loginDto.getUsername());
		
		if(user!=null && user.getPassword().contentEquals(loginDto.getPassword())) {
			
			
		}
		
		
	}

	@Override
	public void signup(SignupDto signupDto) {
		if(signupDto.getPassword().contentEquals(signupDto.getReEnterPassword()))
		{
			User user=userDAO.findByEmail(signupDto.getUsername());
			user.setId(user.getId());
			user.setCreatedBy(user.getCreatedBy());
			user.setCreatedDate(user.getCreatedDate());
			user.setStatus(user.getStatus());
			user.setModifiedBy(user.getEmailId());
			user.setModifiedDate(new Date());
			
			user.setPassword(signupDto.getPassword());
			userDAO.save(user);
		}
	}
	
	
	
}
