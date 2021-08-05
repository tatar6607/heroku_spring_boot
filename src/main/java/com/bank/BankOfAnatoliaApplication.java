package com.bank;

import com.bank.model.Role;
import com.bank.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class BankOfAnatoliaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankOfAnatoliaApplication.class, args);
	}

}


@Component
class DemoCommandLineRunner implements CommandLineRunner {

	@Autowired
	private RoleRepo roleRepo;

	@Override
	public void run(String... args) throws Exception {
		if(roleRepo.findByName("ROLE_ADMIN").isEmpty()) {
			Role roleAdmin = new Role();
			roleAdmin.setName("ROLE_ADMIN");
			roleRepo.save(roleAdmin);
		}

		if(roleRepo.findByName("ROLE_USER").isEmpty()) {
			Role roleUser = new Role();
			roleUser.setName("ROLE_USER");
			roleRepo.save(roleUser);
		}
	}
}