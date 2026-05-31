package julianmejiac.com.pe.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Override
    public void run(String... args) {
        System.out.println("URL      = " + url);
        System.out.println("USERNAME = " + username);
    }
}