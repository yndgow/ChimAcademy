package kr.co.ChimAcademy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer{
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**")
		.addResourceLocations("file:file/");
		registry.addResourceHandler("/elibFile/**") // 전자도서관 파일 경로 추가 - 구홍모 03/16
		.addResourceLocations("file:elibFile/");
		registry.addResourceHandler("/profileThumb/**") // 전자도서관 파일 경로 추가 - 구홍모 03/16
		.addResourceLocations("file:profileThumb/");
	}
}
