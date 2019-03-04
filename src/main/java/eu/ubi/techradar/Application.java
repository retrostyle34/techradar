package eu.ubi.techradar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:4200");
				registry.addMapping("/products/**").allowedOrigins("http://localhost:4200");
//				registry.addMapping("/products").allowedOrigins("http://localhost:4200");
			}
		};
	}

//	@Bean
//	public CorsConfigurationSource corsConfigurationSource() {
//		CorsConfiguration config = new CorsConfiguration();
//		config.applyPermitDefaultValues();
//		config.setAllowCredentials(true);// this line is important it sends only specified domain instead of *
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", config);
//		return source;
//	}


//	@Configuration
//	public class CorsConfiguration {
//
//
//		@Bean
//		public WebMvcConfigurer corsConfigurer() {
//			return new WebMvcConfigurerAdapter() {
//				@Override
//				public void addCorsMappings(CorsRegistry registry) {
//					registry.addMapping("/**")
//							.allowedMethods("HEAD","OPTIONS")
//							.allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept");
//				}
//			};
//		}
//	}

}
