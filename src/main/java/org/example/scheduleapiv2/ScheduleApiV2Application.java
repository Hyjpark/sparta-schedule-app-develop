package org.example.scheduleapiv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ScheduleApiV2Application {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleApiV2Application.class, args);
    }

}
