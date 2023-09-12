package com.pinsoft.mobilbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MobilBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobilBankApplication.class, args);
	}

}
//qweqwe