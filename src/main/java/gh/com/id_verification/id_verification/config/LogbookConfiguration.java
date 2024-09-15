package gh.com.id_verification.id_verification.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.DefaultHttpLogWriter;
import org.zalando.logbook.DefaultSink;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.SplunkHttpLogFormatter;

import static org.zalando.logbook.Conditions.*;
import static org.zalando.logbook.HeaderFilters.authorization;
import static org.zalando.logbook.QueryFilters.accessToken;
import static org.zalando.logbook.QueryFilters.replaceQuery;
import static org.zalando.logbook.json.JsonPathBodyFilters.jsonPath;

@Configuration
public class LogbookConfiguration {

    @Bean
    public Logbook logbook() {
        Logbook logbook = Logbook.builder()
                .condition(exclude(
                        requestTo("/health"),
                        requestTo("/admin/**"),
                        requestTo("/swagger-ui/**"),
                        requestTo("/v3/api-docs/**"),
                        contentType("application/octet-stream")
                        ))
                .queryFilter(accessToken())
                .queryFilter(replaceQuery("password", "<secret>"))
                .bodyFilter(jsonPath("$.password").replace("XXXXX"))
                .headerFilter(authorization())
                .sink(new DefaultSink(
                        new SplunkHttpLogFormatter(),
                        new DefaultHttpLogWriter()
                ))
                .build();
        return logbook;
    }
}
