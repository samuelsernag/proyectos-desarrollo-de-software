import java.math.BigDecimal;

public class PagoTransferencia implements Pago {

    private final String cbu;

    public PagoTransferencia(String cbu) {
        this.cbu = cbu;
    }

    @Override
    public void procesar(BigDecimal monto) {
        System.out.println(
            "Transfiriendo $" + monto + " al CBU " + cbu
        );
    }

    @Override
    public String getDescripcion() {
        return "Transferencia a CBU " + cbu;
    }
}
