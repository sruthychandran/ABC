package com.abinbev.admin.service;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;



@Service
public interface EmailService {

	void sendWelcomeMail(String email, String name);

 
}
