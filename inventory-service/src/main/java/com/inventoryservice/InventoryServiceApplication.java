package com.inventoryservice;

import com.inventoryservice.model.Inventory;
import com.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
		@Bean
        public CommandLineRunner LoadData(InventoryRepository inventoryRepository){
		return args -> {
			try {
				Inventory inventory1 = new Inventory();
				inventory1.setSkuCode("Samsung");
				inventory1.setQuantity(0);

				Inventory inventory2 = new Inventory();
				inventory2.setSkuCode("Nokia");
				inventory2.setQuantity(4);

				inventoryRepository.save(inventory1);
				inventoryRepository.save(inventory2);
			} catch (Exception e) {
				// Handle exception appropriately, e.g., log or print the error
				e.printStackTrace();
			}
		};
	}
}
