package com.github.albertocsm.foggle;

import com.github.albertocsm.foggle.impl.dal.ToggleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.transaction.Transactional;

@SpringBootApplication
public class Application {

    static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner runner(final ToggleRepository repository) {
        return args -> query(repository);
    }

    @Transactional
    public void query(final ToggleRepository repository) {

        log.info("---------------> " + repository.all().toString());
    }
}
