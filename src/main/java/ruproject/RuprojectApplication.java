package ruproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableJpaRepositories
public class RuprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(RuprojectApplication.class, args);
	}

}
