package ntnu.group03.idata2900.ams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class AssetManagementSystemApplication {


	public static void main(String[] args) {
		SpringApplication.run(AssetManagementSystemApplication.class, args);
	}

}