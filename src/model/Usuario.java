package model;

public class Usuario {
    private int id;
    private String nombre;
    private String direccion;
    private String numeroToma;
    private boolean activo;

    public Usuario(int id, String nombre, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
}