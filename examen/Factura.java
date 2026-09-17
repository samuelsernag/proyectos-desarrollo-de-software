package com.example;

public class Factura implements FacturaOperacion {
    private String numero;
    private String concepto;
    private double subtotal;
    private double porcentajeImpuesto;
    private String tipoFactura;
    private Usuario usuarioPropietario;

    public Factura(String numero, String concepto, double subtotal, double porcentajeImpuesto, String tipoFactura, Usuario usuarioPropietario) {
        this.numero = numero;
        this.concepto = concepto;
        this.subtotal = subtotal;
        this.porcentajeImpuesto = porcentajeImpuesto;
        this.tipoFactura = tipoFactura;
        this.usuarioPropietario = usuarioPropietario;
    }

    @Override
    public double calcularImpuesto() {
        return this.subtotal * (this.porcentajeImpuesto / 100.0);
    }

    @Override
    public double calcularTotal() {
        return this.subtotal + calcularImpuesto();
    }

    public String getNumero() { return numero; }
    public String getConcepto() { return concepto; }
    public double getSubtotal() { return subtotal; }
    public double getPorcentajeImpuesto() { return porcentajeImpuesto; }
    public String getTipoFactura() { return tipoFactura; }
    public Usuario getUsuarioPropietario() { return usuarioPropietario; }
}
