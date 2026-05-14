package model;

public class Usuario {

    private int id;
    private String nombre;
    private String direccion;
    private String numeroToma;
    private boolean activo;

    public Usuario(
            int id,
            String nombre,
            String direccion,
            String numeroToma,
            boolean activo
    ) {

        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.numeroToma = numeroToma;
        this.activo = activo;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getNumeroToma() {
        return numeroToma;
    }

    public boolean isActivo() {
        return activo;
    }
}