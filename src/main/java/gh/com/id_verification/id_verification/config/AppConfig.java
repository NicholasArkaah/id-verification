package gh.com.id_verification.id_verification.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("app")
@Setter
@Getter
public class AppConfig {

    private String base_directory;
    private String id_images;
    private String allowed_file_formats;

    private String merchant_key;
    private String id_verification_url;
    private String center;

}
