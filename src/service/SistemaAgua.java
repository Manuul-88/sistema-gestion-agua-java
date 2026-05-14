package service;

import model.Usuario;
import model.Pago;

import java.util.ArrayList;

public class SistemaAgua {

    private ArrayList<Usuario> usuarios;
    private ArrayList<Pago> pagos;

    public SistemaAgua() {
        usuarios = new ArrayList<>();
        pagos = new ArrayList<>();
    }
    // REGISTRAR USUARIO
    public boolean registrarUsuario(
            int id,
            String nombre,
            String direccion,
            String numeroToma,
            boolean activo
    ) {

        // VALIDACIONES
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }

        if (direccion == null || direccion.trim().isEmpty()) {
            return false;
        }

        if (existeUsuario(id)) {
            return false;
        }

        Usuario nuevo = new Usuario(
                id,
                nombre,
                direccion,
                numeroToma,
                activo
        );

        usuarios.add(nuevo);

        return true;
    }
    // REGISTRAR PAGO
    public boolean registrarPago(
            int idUsuario,
            String mes,
            int anio,
            String fechaPago,
            double monto
    ) {

        // VALIDAR USUARIO
        if (!existeUsuario(idUsuario)) {
            return false;
        }

        // VALIDAR MES
        if (mes == null || mes.trim().isEmpty()) {
            return false;
        }

        // VALIDAR MONTO
        if (monto <= 0) {
            return false;
        }

        Pago nuevoPago = new Pago(
                idUsuario,
                mes,
                anio,
                fechaPago,
                monto
        );

        pagos.add(nuevoPago);

        return true;
    }
    // EXISTE USUARIO
    public boolean existeUsuario(int id) {

        for (Usuario u : usuarios) {

            if (u.getId() == id) {
                return true;
            }
        }

        return false;
    }
    // BUSCAR USUARIO
    public Usuario buscarUsuarioPorId(int id) {

        for (Usuario u : usuarios) {

            if (u.getId() == id) {
                return u;
            }
        }

        return null;
    }
    // OBTENER PAGOS POR USUARIO
    public ArrayList<Pago> obtenerPagosUsuario(int idUsuario) {

        ArrayList<Pago> resultado = new ArrayList<>();

        for (Pago p : pagos) {

            if (p.getIdUsuario() == idUsuario) {
                resultado.add(p);
            }
        }

        return resultado;
    }
    // GETTERS
    public ArrayList<Usuario> obtenerUsuarios() {
        return usuarios;
    }

    public ArrayList<Pago> obtenerPagos() {
        return pagos;
    }
}