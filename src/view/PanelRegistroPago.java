package view;

import service.SistemaAgua;

import javax.swing.*;
import java.awt.*;

public class PanelRegistroPago extends JPanel {
    private SistemaAgua sistema;
    private JTextField txtIdUsuario;
    private JTextField txtMes;
    private JTextField txtMonto;
    private JButton btnGuardar;

    public PanelRegistroPago(SistemaAgua sistema) {
        this.sistema = sistema;

        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("ID Usuario:"));
        txtIdUsuario = new JTextField();
        add(txtIdUsuario);

        add(new JLabel("Mes:"));
        txtMes = new JTextField();
        add(txtMes);

        add(new JLabel("Monto:"));
        txtMonto = new JTextField();
        add(txtMonto);

        btnGuardar = new JButton("Guardar Pago");
        add(btnGuardar);

        btnGuardar.addActionListener(e -> guardarPago());
    }

    private void guardarPago() {
        try {
            int idUsuario = Integer.parseInt(txtIdUsuario.getText().trim());
            String mes = txtMes.getText().trim();
            double monto = Double.parseDouble(txtMonto.getText().trim());

            if (mes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Llena todos los campos");
                return;
            }

            sistema.registrarPago(idUsuario, mes, monto);
            JOptionPane.showMessageDialog(this, "Pago registrado correctamente");

            txtIdUsuario.setText("");
            txtMes.setText("");
            txtMonto.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID y monto deben ser numéricos");
        }
    }
}