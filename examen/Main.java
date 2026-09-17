package com.example;

import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = -1;
        do {
            System.out.println("\n--- MENÚ DE OPCIONES ---");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Registrar factura");
            System.out.println("3. Consultar usuarios en base de datos");
            System.out.println("4. Consultar facturas en base de datos");
            System.out.println("5. Calcular impuesto");
            System.out.println("6. Calcular total");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> registrarUsuario();
                    case 2 -> registrarFactura();
                    case 3 -> consultarUsuarios();
                    case 4 -> consultarFacturas();
                    case 5 -> ejecutarCalculo(true);
                    case 6 -> ejecutarCalculo(false);
                    case 0 -> System.out.println("Saliendo del programa...");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            }
        } while (opcion != 0);
    }

    private static void registrarUsuario() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine().trim();

        if (nombre.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
            System.out.println("Error: Ningún campo puede estar vacío.");
            return;
        }

        String sql = "INSERT INTO usuarios (nombre, email, telefono) VALUES (?, ?, ?)";
        try (Connection conn = BaseDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, nombre);
            stmt.setString(2, email);
            stmt.setString(3, telefono);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    System.out.println("Usuario registrado con ID: " + rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error de BD (posible email duplicado): " + e.getMessage());
        }
    }

    private static void registrarFactura() {
        System.out.print("Número de factura: ");
        String numero = scanner.nextLine().trim();
        System.out.print("Concepto: ");
        String concepto = scanner.nextLine().trim();
        System.out.print("Subtotal: ");
        double subtotal = Double.parseDouble(scanner.nextLine());
        System.out.print("Porcentaje de Impuesto (%): ");
        double porcentaje = Double.parseDouble(scanner.nextLine());
        System.out.print("Tipo de factura: ");
        String tipo = scanner.nextLine().trim();
        System.out.print("ID del usuario propietario: ");
        int usuarioId = Integer.parseInt(scanner.nextLine());

        if (numero.isEmpty() || concepto.isEmpty() || tipo.isEmpty() || subtotal < 0 || porcentaje < 0) {
            System.out.println("Error: Datos vacíos o valores numéricos negativos.");
            return;
        }

        String sql = "INSERT INTO facturas (numero, concepto, subtotal, porcentaje_impuesto, tipo_factura, usuario_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = BaseDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, numero);
            stmt.setString(2, concepto);
            stmt.setDouble(3, subtotal);
            stmt.setDouble(4, porcentaje);
            stmt.setString(5, tipo);
            stmt.setInt(6, usuarioId);
            stmt.executeUpdate();

            System.out.println("Factura registrada exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error de BD al registrar factura: " + e.getMessage());
        }
    }

    private static void consultarUsuarios() {
        String sql = "SELECT * FROM usuarios";
        try (Connection conn = BaseDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n--- LISTA DE USUARIOS ---");
            while (rs.next()) {
                System.out.printf("ID: %d | Nombre: %s | Email: %s | Tel: %s%n",
                        rs.getInt("id"), rs.getString("nombre"), rs.getString("email"), rs.getString("telefono"));
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar usuarios: " + e.getMessage());
        }
    }

    private static void consultarFacturas() {
        String sql = "SELECT f.*, u.nombre FROM facturas f JOIN usuarios u ON f.usuario_id = u.id";
        try (Connection conn = BaseDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n--- LISTA DE FACTURAS ---");
            while (rs.next()) {
                Usuario user = new Usuario(rs.getInt("usuario_id"), rs.getString("nombre"), "", "");
                FacturaOperacion f = new Factura(
                        rs.getString("numero"), rs.getString("concepto"),
                        rs.getDouble("subtotal"), rs.getDouble("porcentaje_impuesto"),
                        rs.getString("tipo_factura"), user
                );

                System.out.printf("Número: %s | Concepto: %s | Subtotal: %.2f | Impuesto: %.2f | Total: %.2f | Propietario: %s%n",
                        rs.getString("numero"), rs.getString("concepto"), rs.getDouble("subtotal"),
                        f.calcularImpuesto(), f.calcularTotal(), user.getNombre());
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar facturas: " + e.getMessage());
        }
    }

    private static void ejecutarCalculo(boolean esImpuesto) {
        System.out.print("Ingrese el subtotal: ");
        double subtotal = Double.parseDouble(scanner.nextLine());
        System.out.print("Ingrese el porcentaje de impuesto: ");
        double porcentaje = Double.parseDouble(scanner.nextLine());

        // Polimorfismo usando la interfaz FacturaOperacion
        FacturaOperacion op = new Factura("TMP", "Calculo temporal", subtotal, porcentaje, "N/A", null);

        if (esImpuesto) {
            System.out.printf("Monto de Impuesto calculado: %.2f%n", op.calcularImpuesto());
        } else {
            System.out.printf("Total de la Factura calculado: %.2f%n", op.calcularTotal());
        }
    }
}
