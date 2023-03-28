package com.aldia.aldia;

import com.aldia.aldia.Controller.DatabaseController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AldiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AldiaApplication.class, args);

		DatabaseController databaseController = DatabaseController.getInstance();

		databaseController.inicializarBD();
	}

}
