package io.github.nhannht.webquizzangular;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class WebQuizzAngularApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebQuizzAngularApplication.class, args);
    }
    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}
