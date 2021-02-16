package com.abinbev.admin.config;


import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageResolver {
private static final String LOCAL_HEADER = "locale";
@Autowired private MessageSource messageSource;

@Autowired private HttpServletRequest httpServletRequest;

public String resolveKey(String key) {
return messageSource.getMessage(key, null, resolveLocale());
}

private Locale resolveLocale() {
String locale = httpServletRequest.getHeader(LOCAL_HEADER);
if (locale == null) {
return Locale.getDefault();
}
return new Locale(locale);
}
}