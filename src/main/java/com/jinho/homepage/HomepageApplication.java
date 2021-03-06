package com.jinho.homepage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HomepageApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(HomepageApplication.class, args);
	}

	@Value("${spring.profiles.active}")
	private String profiles;


	// todo : 실제 서버에 업로드시 변경해야함.
	@Bean(name = "uploadPath")
	public String uploadPath() {
		if (profiles.equals("local")) {
			return "D:/testFolder/";
		} else if (profiles.equals("dev")) {
			return "/home/ubuntu/2107homepage/image";
		} else if (profiles.equals("prod")) {
			return "/";
		}
		return "/";
	}
}
