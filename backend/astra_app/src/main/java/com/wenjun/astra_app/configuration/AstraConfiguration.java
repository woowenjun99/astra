package com.wenjun.astra_app.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class AstraConfiguration {
    @Value("${firebaseServiceAccount}")
    private String serviceAccount;
    @Value("${baseUrl}")
    private String baseUrl;

    @Bean
    public FirebaseAuth firebaseAuth() throws IOException {
        InputStream serviceAccount = new ByteArrayInputStream(this.serviceAccount.getBytes());
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(serviceAccount);
        FirebaseOptions firebaseOptions = FirebaseOptions.builder().setCredentials(googleCredentials).build();
        FirebaseApp firebaseApp = FirebaseApp.initializeApp(firebaseOptions);
        return FirebaseAuth.getInstance(firebaseApp);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedOrigins(baseUrl)
                        .allowedMethods("POST", "OPTIONS", "GET", "PUT")
                        .allowedHeaders("*");
            }
        };
    }
}
