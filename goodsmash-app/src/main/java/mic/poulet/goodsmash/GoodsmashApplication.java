package mic.poulet.goodsmash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class GoodsmashApplication {

	public static void main(String[] args) {

		SpringApplication.run(GoodsmashApplication.class, args);
	}
}
