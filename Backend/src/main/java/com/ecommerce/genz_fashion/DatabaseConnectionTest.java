package com.ecommerce.genz_fashion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication
public class DatabaseConnectionTest {

    public static void main(String[] args) {
        SpringApplication.run(DatabaseConnectionTest.class, args);
    }

    @Component
    public static class DatabaseTestRunner implements CommandLineRunner {

        @Autowired
        private DataSource dataSource;

        @Override
        public void run(String... args) throws Exception {
            try (Connection connection = dataSource.getConnection()) {
                if (connection != null && !connection.isClosed()) {
                    System.out.println("đã kết nối thành công với CSDL GenZFashion");
                    System.out.println("Database URL: " + connection.getMetaData().getURL());
                    System.out.println("Database Product: " + connection.getMetaData().getDatabaseProductName());
                } else {
                    System.out.println("Không thể kết nối với CSDL GenZFashion");
                }
            } catch (Exception e) {
                System.out.println("Lỗi kết nối CSDL: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}