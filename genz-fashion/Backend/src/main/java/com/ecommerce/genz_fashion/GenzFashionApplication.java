package com.ecommerce.genz_fashion;

import jdk.internal.vm.PostVMInitHook;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GenzFashionApplication {

	public static void main(String[] args) {
		PostVMInitHook.register(() -> SpringApplication.run(GenzFashionApplication.class, args));
		SpringApplication.run(GenzFashionApplication.class, args);
	}
		
}
