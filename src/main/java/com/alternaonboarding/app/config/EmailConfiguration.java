//package com.alternaonboarding.app.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//@Configuration
//public class EmailConfiguration {
//    @Bean
//    public JavaMailSender javaMailSender() {
//        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//        javaMailSender.setHost("smtp.gmail.com");
//        javaMailSender.setPort(587);
//        javaMailSender.setUsername("kevinpeter0057@gmail.com");
//        javaMailSender.setPassword("Athena0057");
//        javaMailSender.getJavaMailProperties().setProperty("mail.smtp.auth", "true");
//        javaMailSender.getJavaMailProperties().setProperty("mail.smtp.starttls.enable", "true");
//        return javaMailSender;
//        }
//}
