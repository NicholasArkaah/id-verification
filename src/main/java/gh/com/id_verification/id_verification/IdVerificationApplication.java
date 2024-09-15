package gh.com.id_verification.id_verification;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@OpenAPIDefinition(
		info = @Info(title = "ID Verification Services",
				version = "1.0",
				contact = @Contact(
						name = "ID Verifications services",
						url = "https://id-verification-services.com",
						email = "support@id-verification-services.com"),
				description = "ID Verification Services"))
@SpringBootApplication
public class IdVerificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdVerificationApplication.class, args);
	}

}
