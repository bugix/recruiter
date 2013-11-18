package ch.itraum.recruiter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.spring3.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import ch.itraum.recruiter.controller.FrontendController;

@Configuration
@ComponentScan(basePackageClasses = { FrontendController.class })
public class WebConfig extends WebMvcConfigurationSupport {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Bean
	public ThymeleafViewResolver viewResolver() {
		ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
		thymeleafViewResolver.setTemplateEngine(templateEngine());
		thymeleafViewResolver.setCharacterEncoding("UTF-8");
		return thymeleafViewResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
		springTemplateEngine.setTemplateResolver(templateResolver());
		return springTemplateEngine;
	}

	@Bean
	public ServletContextTemplateResolver templateResolver() {
		ServletContextTemplateResolver servletContextTemplateResolver = new ServletContextTemplateResolver();
		servletContextTemplateResolver.setPrefix("/WEB-INF/view/");
		servletContextTemplateResolver.setSuffix(".html");
		servletContextTemplateResolver.setTemplateMode("HTML5");
		servletContextTemplateResolver.setCharacterEncoding("UTF-8");
		servletContextTemplateResolver.setCacheable(false);
		return servletContextTemplateResolver;
	}
	
	@Bean
	public StandardServletMultipartResolver multipartResolver() {
		StandardServletMultipartResolver standardServletMultipartResolver = new StandardServletMultipartResolver();
		
		return standardServletMultipartResolver;
	}

}
