import java.math.BigDecimal;

public class PagoEfectivo implements Pago {

    @Override
    public void procesar(BigDecimal monto) {
        System.out.println(
            "Registrando pago en efectivo de $" + monto
        );
    }

    @Override
    public String getDescripcion() {
        return "Efectivo";
    }
}
