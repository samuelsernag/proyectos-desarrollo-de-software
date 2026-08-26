import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== CUENTA ===");

        // Crear una cuenta con saldo inicial
        Cuenta cuenta = new Cuenta(
                "001",
                new BigDecimal("100000")
        );

        System.out.println("Saldo inicial: " + cuenta.getSaldo());

        // Depositar
        cuenta.depositar(new BigDecimal("50000"));
        System.out.println("Después de depositar: " + cuenta.getSaldo());

        // Debitar
        cuenta.debitar(new BigDecimal("30000"));
        System.out.println("Después de debitar: " + cuenta.getSaldo());


        System.out.println("\n=== CUENTA CORRIENTE ===");

        // Crear una cuenta corriente
        CuentaCorriente cuentaCorriente = new CuentaCorriente(
                "002",
                new BigDecimal("100000"),
                new BigDecimal("50000")
        );

        System.out.println(
                "Saldo inicial: " + cuentaCorriente.getSaldo()
        );

        // Usar el límite de descubierto
        cuentaCorriente.debitar(new BigDecimal("120000"));

        System.out.println(
                "Saldo después del débito: "
                + cuentaCorriente.getSaldo()
        );


        System.out.println("\n=== MÉTODOS DE PAGO ===");

        // Pago con tarjeta
        Pago pagoTarjeta = new PagoTarjeta("123456789");
        pagoTarjeta.procesar(new BigDecimal("50000"));
        System.out.println(pagoTarjeta.getDescripcion());

        // Pago por transferencia
        Pago pagoTransferencia =
                new PagoTransferencia("987654321");

        pagoTransferencia.procesar(new BigDecimal("75000"));
        System.out.println(pagoTransferencia.getDescripcion());

        // Pago en efectivo
        Pago pagoEfectivo = new PagoEfectivo();

        pagoEfectivo.procesar(new BigDecimal("25000"));
        System.out.println(pagoEfectivo.getDescripcion());
    }
}
