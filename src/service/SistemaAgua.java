package service;

import model.Usuario;
import model.Pago;
import java.util.ArrayList;

public class SistemaAgua {
    private ArrayList<Usuario> usuarios;
    private ArrayList<Pago> pagos;
    private int siguienteIdUsuario;

    public SistemaAgua() {
        usuarios = new ArrayList<>();
        pagos = new ArrayList<>();
        siguienteIdUsuario = 1;
    }

    public void registrarUsuario(String nombre, String direccion) {
        Usuario nuevoUsuario = new Usuario(siguienteIdUsuario, nombre, direccion);
        usuarios.add(nuevoUsuario);
        siguienteIdUsuario++;
    }

    public void registrarPago(int idUsuario, String mes, double monto) {
        pagos.add(new Pago(idUsuario, mes, monto));
    }

    public ArrayList<Usuario> obtenerUsuarios() {
        return usuarios;
    }

    public ArrayList<Pago> obtenerPagos() {
        return pagos;
    }
}