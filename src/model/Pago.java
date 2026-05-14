package model;

public class Pago {
    private int idUsuario;
    private String mes;
    private int anio;
    private String fechaPago;
    private double monto;

    public Pago(int idUsuario, String mes, double monto) {
        this.idUsuario = idUsuario;
        this.mes = mes;
        this.monto = monto;
    }

    public int getIdUsuario() { return idUsuario; }
    public String getMes() { return mes; }
    public double getMonto() { return monto; }
}