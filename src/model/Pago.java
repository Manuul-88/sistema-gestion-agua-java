package model;

public class Pago {

    private int idUsuario;
    private String mes;
    private int anio;
    private String fechaPago;
    private double monto;

    public Pago(
            int idUsuario,
            String mes,
            int anio,
            String fechaPago,
            double monto
    ) {

        this.idUsuario = idUsuario;
        this.mes = mes;
        this.anio = anio;
        this.fechaPago = fechaPago;
        this.monto = monto;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getMes() {
        return mes;
    }

    public int getAnio() {
        return anio;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public double getMonto() {
        return monto;
    }
}