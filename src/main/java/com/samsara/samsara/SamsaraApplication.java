package com.samsara.samsara;

import com.samsara.samsara.constants.Type;
import com.samsara.samsara.constants.City;
import java.net.Proxy;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@SpringBootApplication()
public class SamsaraApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SamsaraApplication.class);

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*"); // this allows all origin
        config.addAllowedHeader("*"); // this allows all headers
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(SamsaraApplication.class, args);
        LOGGER.info("Simple log statement with inputs {}, {} and {}", 1, 2, 3);
    }
    @EventListener(ApplicationReadyEvent.class)
public void doSomethingAfterStartup() {
    System.out.println("hello world, I have just started up");
    servletContext.setAttribute("msa msa","kywsa");
    servletContext.setAttribute("types",Type.values());
    servletContext.setAttribute("cities",City.values());
}
    @Autowired
    AuthenticationSuccessHandlerImpl onsuccess;

    @Autowired
    ServletContext servletContext;
}
