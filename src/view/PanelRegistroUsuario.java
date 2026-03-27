package view;

import service.SistemaAgua;

import javax.swing.*;
import java.awt.*;

public class PanelRegistroUsuario extends JPanel {
    private SistemaAgua sistema;
    private JTextField txtNombre;
    private JTextField txtDireccion;
    private JButton btnGuardar;

    public PanelRegistroUsuario(SistemaAgua sistema) {
        this.sistema = sistema;

        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        add(txtNombre);

        add(new JLabel("Dirección:"));
        txtDireccion = new JTextField();
        add(txtDireccion);

        btnGuardar = new JButton("Guardar Usuario");
        add(btnGuardar);

        btnGuardar.addActionListener(e -> guardarUsuario());
    }

    private void guardarUsuario() {
        String nombre = txtNombre.getText().trim();
        String direccion = txtDireccion.getText().trim();

        if (nombre.isEmpty() || direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Llena todos los campos");
            return;
        }

        sistema.registrarUsuario(nombre, direccion);
        JOptionPane.showMessageDialog(this, "Usuario registrado correctamente");

        txtNombre.setText("");
        txtDireccion.setText("");
    }
}