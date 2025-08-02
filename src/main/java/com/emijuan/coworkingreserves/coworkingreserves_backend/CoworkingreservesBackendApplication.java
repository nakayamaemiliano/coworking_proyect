package com.emijuan.coworkingreserves.coworkingreserves_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CoworkingreservesBackendApplication {


	public static void main(String[] args) {
		SpringApplication.run(CoworkingreservesBackendApplication.class, args);



	}

	@GetMapping("/")
	public String saludar(){
		return "Hola";
	}




}



