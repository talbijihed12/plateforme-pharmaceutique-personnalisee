package com.project.pppmagasinmicroservice;

import com.project.pppmagasinmicroservice.Config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
@Import(SwaggerConfiguration.class)
@EnableSwagger2


public class PppMagasinMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PppMagasinMicroserviceApplication.class, args);
    }

}
