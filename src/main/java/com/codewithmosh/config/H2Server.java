package com.codewithmosh.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class H2Server {

    public static void start() {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:h2:~/parcial2",
                "sa",
                "sa")) {

            System.out.println("H2 embebido iniciado");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}