package com.example.BloodBank;

import com.example.BloodBank.service.AdminService;
import com.example.BloodBank.service.BloodStockService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BloodBankApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(BloodBankApplication.class, args);
		AdminService admin = run.getBean(AdminService.class);
//		AdminService admin = new AdminService(); //this will show error
		admin.setAdmin();
		System.out.println("Application Started");

		BloodStockService bloodStockService = run.getBean((BloodStockService.class));
		bloodStockService.setBloodStock();

	}

	//ModelMapper ki dependency inject krne ke baad bhi hme iska bean bnana hai. Here below I made bean by beanFactory method
	@Bean
	public ModelMapper getModelMapperInstance(){
		return new ModelMapper();
	}

}
