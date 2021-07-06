package com.cms;

import java.util.Arrays;

import com.cms.models.Role;
import com.cms.models.User;
import com.cms.services.UserService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCrypt;

@SpringBootApplication
@EnableMongoRepositories
public class CmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmsApplication.class, args);
	}

	
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			

			System.out.println("Let's inspect the beans provided by Spring Boot:");
		
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}
			for(int i = 0 ; i < 6 ; i++){
				try{
					User user = new User(""+Math.random()*i*10, "mail"+i+Math.random()*10,BCrypt.hashpw("password", BCrypt.gensalt()), 0L, true, true, Role.ADMIN);
					ctx.getBean(UserService.class).insertElement(user);
				}catch(Exception e){
					System.out.println(e);
				}
			}
			
			
			

		};
	}


	// @Bean
	// CommandLineRunner commandLineRunner(UserService service){
	// 	return args->{
	// 		User user = new User("name", "mail", "password", 0L, true, true, Role.ADMIN, LocalDate.now());
	// 		service.insertElement(user);

	// 	};
	// }
}
