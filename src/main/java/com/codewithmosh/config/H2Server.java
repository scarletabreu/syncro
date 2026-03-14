package com.codewithmosh.config;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.concurrent.ExecutionException;

//Clase para inicializar h2 de manera automatica, siempre que se ejecute la aplicacion.
public class H2Server {
    public static void start(){
        try{
            Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092").start();
            System.out.print("H2 iniciado");
            try {
                Connection conn = DriverManager.getConnection("jdbc:h2:~/parcial2", "sa", "sa");
            } catch (Exception e){
                System.out.print("No se creo el server.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
