package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDatos {
    private static final String URL = "jdbc:postgresql://localhost:5432/tu_base_datos";
    private static final String USUARIO = "postgres";
    private static final String CLAVE = "tu_contraseña";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}
