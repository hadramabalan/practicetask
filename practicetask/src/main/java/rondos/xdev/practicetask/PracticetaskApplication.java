package rondos.xdev.practicetask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import rondos.xdev.practicetask.config.SecurityConfig;

@SpringBootApplication
@Import(SecurityConfig.class)
@EnableJpaRepositories
public class PracticetaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(PracticetaskApplication.class, args);
    }

}
