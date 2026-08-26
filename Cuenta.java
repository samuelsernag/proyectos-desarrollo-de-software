import java.math.BigDecimal;

public class Cuenta {
    protected final String numero;
    protected BigDecimal saldo;

    public Cuenta(String numero, BigDecimal saldoInicial) {
        this.numero = numero;
        this.saldo = saldoInicial;
    }

    public void depositar(BigDecimal monto) {
        this.saldo = this.saldo.add(monto);
    }

    public void debitar(BigDecimal monto) {
        if (monto.compareTo(this.saldo) > 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente");
        }

        this.saldo = this.saldo.subtract(monto);
    }

    public BigDecimal getSaldo() {
        return saldo;
    }
}
