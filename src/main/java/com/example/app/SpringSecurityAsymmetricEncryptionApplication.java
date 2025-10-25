package com.example.app;

import com.example.app.role.Role;
import com.example.app.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootApplication
public class SpringSecurityAsymmetricEncryptionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityAsymmetricEncryptionApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner runner(final RoleRepository repository){
//		return args -> {
//			final Optional<Role> userRole = repository.findByName("ROLE_USER");
//			if(userRole.isEmpty()){
//				Role role = new Role();
//				role.setName("ROLE_USER");
//				role.setCreatedBy("ADMIN");
//				repository.save(role);
//			}
//		};
//	}

}
