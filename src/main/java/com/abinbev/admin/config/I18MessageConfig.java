package com.abinbev.admin.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@ComponentScan
@Configuration
@EnableAutoConfiguration
public class I18MessageConfig {
  @Bean
  public MessageSource messageSource() {

    ReloadableResourceBundleMessageSource messageSource =
        new ReloadableResourceBundleMessageSource();

    messageSource.setBasename("classpath:messages/messages");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }

	/*
	 * @Primary
	 * 
	 * @Bean public LocalValidatorFactoryBean getValidator() {
	 * LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
	 * bean.setValidationMessageSource(messageSource()); return bean; }
	 */
}