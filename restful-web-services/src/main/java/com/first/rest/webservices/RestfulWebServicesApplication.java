package com.first.rest.webservices;

import com.first.rest.webservices.config.TimeLoggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.*;
import org.springframework.http.converter.feed.AtomFeedHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class RestfulWebServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestfulWebServicesApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean(name = "restTemplate")
	public RestTemplate restTemplate() {
		RestTemplate template = new RestTemplate();
		HttpComponentsClientHttpRequestFactory requestFactory =
				new HttpComponentsClientHttpRequestFactory();
		requestFactory.setConnectTimeout(Integer.parseInt("60000"));
		template.setRequestFactory(requestFactory);
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();

		SourceHttpMessageConverter<?> sourceConverter = new SourceHttpMessageConverter<>();
		AtomFeedHttpMessageConverter atomFeedHttpMessageConverter = new AtomFeedHttpMessageConverter();
		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
		ByteArrayHttpMessageConverter byteArrayHttpMessageConverter =
				new ByteArrayHttpMessageConverter();
		FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
		ResourceHttpMessageConverter resourceHttpMessageConverter = new ResourceHttpMessageConverter();
		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter =
				new MappingJackson2HttpMessageConverter();

		messageConverters.add(sourceConverter);
		messageConverters.add(atomFeedHttpMessageConverter);
		messageConverters.add(stringHttpMessageConverter);
		messageConverters.add(byteArrayHttpMessageConverter);
		messageConverters.add(formHttpMessageConverter);
		messageConverters.add(resourceHttpMessageConverter);
		messageConverters.add(mappingJackson2HttpMessageConverter);

		template.setMessageConverters(messageConverters);
		return template;
	}

	@Bean
	public TimeLoggerConfig timeLogger() {
		return new TimeLoggerConfig();
	}
}
