    // Database connection test
package com.ecommerce.genz_fashion;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication
public class GenzFashionApplication
{
    public static void main(String[] args) {
        SpringApplication.run(GenzFashionApplication.class, args);
    }

    @Bean
    public CommandLineRunner databaseConnectionTest(DataSource dataSource) {
        return args -> {
            System.out.println("==================================================");
            System.out.println("CHECKING DATABASE CONNECTION...");
            try (Connection connection = dataSource.getConnection()) {
                if (connection != null && !connection.isClosed()) {
                    System.out.println("✅ Successfully connected to the database!");
                    System.out.println("   Database URL: " + connection.getMetaData().getURL());
                }
            } catch (Exception e) {
                System.err.println("❌ DATABASE CONNECTION ERROR: " + e.getMessage());
            }
            System.out.println("==================================================");
        };
    }
}
