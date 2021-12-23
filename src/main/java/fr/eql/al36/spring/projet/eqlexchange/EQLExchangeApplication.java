package fr.eql.al36.spring.projet.eqlexchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class EQLExchangeApplication extends SpringBootServletInitializer  {

    public static void main(String[] args) {
        SpringApplication.run(EQLExchangeApplication.class, args);
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    	return application.sources(EQLExchangeApplication.class);
    }

}
