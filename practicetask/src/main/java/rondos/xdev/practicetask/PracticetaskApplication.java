package rondos.xdev.practicetask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class PracticetaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(PracticetaskApplication.class, args);
    }

}
