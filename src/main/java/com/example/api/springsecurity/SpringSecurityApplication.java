package com.example.api.springsecurity;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The type Spring security application.
 */
@SpringBootApplication
public class SpringSecurityApplication {

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(SpringSecurityApplication.class, args);
  }


  /**
   * Create passwords command line runner.
   *
   * @param passwordEncoder the password encoder
   * @return the command line runner
   */
  @Bean
  public CommandLineRunner createPasswords(PasswordEncoder passwordEncoder) {
    return args -> {
      System.out.println(passwordEncoder.encode("clave123"));
      System.out.println(passwordEncoder.encode("clave456"));
      System.out.println(passwordEncoder.encode("clave789"));
    };

  }

}
