package home.app.services.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"home.app.grpc.api", "home.app.services.service"})
@EnableJpaRepositories(basePackages = {"home.app.grpc.api.repositories", "home.app.services.service.repositories"})
@EnableTransactionManagement
@EnableScheduling
public class ServicesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicesServiceApplication.class, args);
    }

}
