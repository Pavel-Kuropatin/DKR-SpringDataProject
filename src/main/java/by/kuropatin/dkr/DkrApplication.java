package by.kuropatin.dkr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "by.kuropatin.dkr")
@EntityScan(basePackages = "by.kuropatin.dkr")
@EnableJpaRepositories(basePackages = "by.kuropatin.dkr")
@EnableTransactionManagement
public class DkrApplication {

    public static void main(String[] args) {
        SpringApplication.run(DkrApplication.class, args);
    }
}