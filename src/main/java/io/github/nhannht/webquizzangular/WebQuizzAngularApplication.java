
package io.github.nhannht.webquizzangular;

import io.github.nhannht.webquizzangular.entity.User;
import io.github.nhannht.webquizzangular.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class WebQuizzAngularApplication {

    @Autowired
    UserRepository userRepository ;

    public static void main(String[] args) {
        SpringApplication.run(WebQuizzAngularApplication.class, args);
    }
    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner loadData(){
        return (args) -> {
            User user = new User();
            user.setEmail("nhanclassroom@gmail.com");
            user.setPassword("$2a$10$0QJmj1b4W78UXzR4ZDY5rOxP7rXiV9X2NWjVNmP4MNc3ZuwnZvQna");
            userRepository.save(user);
        };
    }
}
