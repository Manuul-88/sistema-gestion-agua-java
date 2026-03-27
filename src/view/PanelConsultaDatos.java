package view;

import model.Usuario;
import model.Pago;
import service.SistemaAgua;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelConsultaDatos extends JPanel {

    private SistemaAgua sistema;

    private JTable tablaUsuarios;
    private JTable tablaPagos;

    private DefaultTableModel modeloUsuarios;
    private DefaultTableModel modeloPagos;

    private JTextField txtNombre;
    private JTextField txtDireccion;

    private JTextField txtPagoIdUsuario;
    private JTextField txtMes;
    private JTextField txtMonto;

    private JButton btnAgregarUsuario;
    private JButton btnAgregarPago;
    private JButton btnActualizar;

    public PanelConsultaDatos(SistemaAgua sistema) {
        this.sistema = sistema;

        setLayout(new BorderLayout(10, 10));

        modeloUsuarios = new DefaultTableModel(new String[]{"ID", "Nombre", "Dirección"}, 0);
        modeloPagos = new DefaultTableModel(new String[]{"ID Usuario", "Mes", "Monto"}, 0);

        tablaUsuarios = new JTable(modeloUsuarios);
        tablaPagos = new JTable(modeloPagos);

        JPanel panelTablas = new JPanel(new GridLayout(2, 1, 10, 10));

        JPanel panelUsuariosTabla = new JPanel(new BorderLayout());
        panelUsuariosTabla.setBorder(BorderFactory.createTitledBorder("Tabla de Usuarios"));
        panelUsuariosTabla.add(new JScrollPane(tablaUsuarios), BorderLayout.CENTER);

        JPanel panelPagosTabla = new JPanel(new BorderLayout());
        panelPagosTabla.setBorder(BorderFactory.createTitledBorder("Tabla de Pagos"));
        panelPagosTabla.add(new JScrollPane(tablaPagos), BorderLayout.CENTER);

        panelTablas.add(panelUsuariosTabla);
        panelTablas.add(panelPagosTabla);

        JPanel formularioUsuarios = new JPanel(new GridLayout(3, 2, 5, 5));
        formularioUsuarios.setBorder(BorderFactory.createTitledBorder("Captura de Usuario"));

        txtNombre = new JTextField();
        txtDireccion = new JTextField();
        btnAgregarUsuario = new JButton("Agregar Usuario");

        formularioUsuarios.add(new JLabel("Nombre:"));
        formularioUsuarios.add(txtNombre);
        formularioUsuarios.add(new JLabel("Dirección:"));
        formularioUsuarios.add(txtDireccion);
        formularioUsuarios.add(new JLabel(""));
        formularioUsuarios.add(btnAgregarUsuario);

        JPanel formularioPagos = new JPanel(new GridLayout(4, 2, 5, 5));
        formularioPagos.setBorder(BorderFactory.createTitledBorder("Captura de Pago"));

        txtPagoIdUsuario = new JTextField();
        txtMes = new JTextField();
        txtMonto = new JTextField();
        btnAgregarPago = new JButton("Agregar Pago");

        formularioPagos.add(new JLabel("ID Usuario:"));
        formularioPagos.add(txtPagoIdUsuario);
        formularioPagos.add(new JLabel("Mes:"));
        formularioPagos.add(txtMes);
        formularioPagos.add(new JLabel("Monto:"));
        formularioPagos.add(txtMonto);
        formularioPagos.add(new JLabel(""));
        formularioPagos.add(btnAgregarPago);

        JPanel panelFormularios = new JPanel(new GridLayout(1, 2, 10, 10));
        panelFormularios.add(formularioUsuarios);
        panelFormularios.add(formularioPagos);

        btnActualizar = new JButton("Actualizar");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnActualizar);

        btnAgregarUsuario.addActionListener(e -> agregarUsuario());
        btnAgregarPago.addActionListener(e -> agregarPago());
        btnActualizar.addActionListener(e -> actualizarTablas());

        add(panelFormularios, BorderLayout.NORTH);
        add(panelTablas, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        actualizarTablas();
    }

    private void agregarUsuario() {
        String nombre = txtNombre.getText().trim();
        String direccion = txtDireccion.getText().trim();

        if (nombre.isEmpty() || direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos del usuario.");
            return;
        }

        sistema.registrarUsuario(nombre, direccion);

        txtNombre.setText("");
        txtDireccion.setText("");

        actualizarTablas();
        JOptionPane.showMessageDialog(this, "Usuario agregado correctamente.");
    }

    private void agregarPago() {
        String idUsuarioTexto = txtPagoIdUsuario.getText().trim();
        String mes = txtMes.getText().trim();
        String montoTexto = txtMonto.getText().trim();

        if (idUsuarioTexto.isEmpty() || mes.isEmpty() || montoTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos del pago.");
            return;
        }

        try {
            int idUsuario = Integer.parseInt(idUsuarioTexto);
            double monto = Double.parseDouble(montoTexto);

            sistema.registrarPago(idUsuario, mes, monto);

            txtPagoIdUsuario.setText("");
            txtMes.setText("");
            txtMonto.setText("");

            actualizarTablas();
            JOptionPane.showMessageDialog(this, "Pago agregado correctamente.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID Usuario y Monto deben ser números.");
        }
    }

    private void actualizarTablas() {
        modeloUsuarios.setRowCount(0);
        modeloPagos.setRowCount(0);

        for (Usuario usuario : sistema.obtenerUsuarios()) {
            modeloUsuarios.addRow(new Object[]{
                    usuario.getId(),
                    usuario.getNombre(),
                    usuario.getDireccion()
            });
        }

        for (Pago pago : sistema.obtenerPagos()) {
            modeloPagos.addRow(new Object[]{
                    pago.getIdUsuario(),
                    pago.getMes(),
                    pago.getMonto()
            });
        }
    }
}