package home.app.household.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"home.app.grpc.api", "home.app.household.service"})
@EnableJpaRepositories(basePackages = {"home.app.grpc.api.repositories", "home.app.household.service.repositories"})
@EnableTransactionManagement
@EnableScheduling
public class HouseholdServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HouseholdServiceApplication.class, args);
    }

}
