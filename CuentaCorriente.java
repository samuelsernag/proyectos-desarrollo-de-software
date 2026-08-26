import java.math.BigDecimal;

public class CuentaCorriente extends Cuenta {

    private BigDecimal limiteDescubierto;

    public CuentaCorriente(
            String numero,
            BigDecimal saldoInicial,
            BigDecimal limiteDescubierto) {

        super(numero, saldoInicial);
        this.limiteDescubierto = limiteDescubierto;
    }

    @Override
    public void debitar(BigDecimal monto) {
        BigDecimal saldoDisponible = this.saldo.add(limiteDescubierto);

        if (monto.compareTo(saldoDisponible) > 0) {
            throw new SaldoInsuficienteException(
                    "Supera el límite de descubierto"
            );
        }

        this.saldo = this.saldo.subtract(monto);
    }
}
