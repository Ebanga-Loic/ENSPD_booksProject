package com.enspd.books.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.enspd.books.common.entity", "com.enspd.books.admin.user"})
public class EnspdBooksAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnspdBooksAdminApplication.class, args);
	}

}
