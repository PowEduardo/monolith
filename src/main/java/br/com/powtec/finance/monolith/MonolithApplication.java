package br.com.powtec.finance.monolith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"br.com.powtec.finance", "br.com.powtec.finance.database.library.mapper"})
@EnableJpaRepositories(
    basePackages = "br.com.powtec.finance.database.library.repository"
)
@EntityScan(basePackages = {"br.com.powtec.finance.database.library.model"})
public class MonolithApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonolithApplication.class, args);
	}
}
