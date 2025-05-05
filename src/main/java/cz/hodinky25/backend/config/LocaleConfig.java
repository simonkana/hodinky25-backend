package cz.hodinky25.backend.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class LocaleConfig {

    @PostConstruct
    public void init() {
        Locale.setDefault(Locale.ENGLISH);
    }
}