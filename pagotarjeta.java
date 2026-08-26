import java.math.BigDecimal;

public class PagoTarjeta implements Pago {

    private final String numeroTarjeta;

    public PagoTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    @Override
    public void procesar(BigDecimal monto) {
        System.out.println(
            "Cargando $" + monto + " a la tarjeta " + numeroTarjeta
        );
    }

    @Override
    public String getDescripcion() {
        return "Tarjeta " + numeroTarjeta;
    }
}
