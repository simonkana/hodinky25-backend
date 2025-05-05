package cz.hodinky25.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class LoggerConfig {

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);       // tělo requestu
        loggingFilter.setMaxPayloadLength(10000);    // max. délka těla
        loggingFilter.setIncludeHeaders(false);      // nastav true, pokud chceš i hlavičky
        return loggingFilter;
    }
}
