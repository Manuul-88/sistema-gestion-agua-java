package view;

import service.SistemaAgua;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class VentanaPrincipal extends JFrame {

    private JPanel contentPanel;
    private CardLayout cardLayout;

    private DefaultTableModel modeloUsuarios;
    private DefaultTableModel modeloPagos;
    private JComboBox<String> cbIdsDisponibles;
    private JComboBox<String> cbMeses;

    private final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private final Color SIDEBAR_COLOR = new Color(33, 47, 61);
    private final Color BG_COLOR = new Color(245, 245, 245);

    private SistemaAgua sistema;

    public VentanaPrincipal(SistemaAgua sistema) {
        this.sistema = sistema;

        setTitle("Sistema de Gestión de Agua");
        setSize(1150, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        modeloUsuarios = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Dirección", "Toma #", "Estado"}, 0
        );

        modeloPagos = new DefaultTableModel(
                new String[]{"ID Usuario", "Mes", "Año", "Monto", "Fecha Pago"}, 0
        );

        cbIdsDisponibles = new JComboBox<>();
        cbMeses = new JComboBox<>(new String[]{
                "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        });

        JPanel sidebar = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        sidebar.setBackground(SIDEBAR_COLOR);
        sidebar.setPreferredSize(new Dimension(250, 750));

        sidebar.add(crearBotonMenu("Registrar Usuario", "USUARIO"));
        sidebar.add(crearBotonMenu("Registrar Pago", "PAGO"));
        sidebar.add(crearBotonMenu("Historial", "HISTORIAL"));
        sidebar.add(crearBotonMenu("Morosos / Adeudos", "MOROSOS"));
        sidebar.add(crearBotonMenu("Reportes", "REPORTES"));

        add(sidebar, BorderLayout.WEST);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        contentPanel.add(crearVistaUsuario(), "USUARIO");
        contentPanel.add(crearVistaPago(), "PAGO");
        contentPanel.add(crearVistaTablaGenerica("Historial de Pagos", modeloPagos), "HISTORIAL");
        contentPanel.add(crearVistaMorosos(), "MOROSOS");
        contentPanel.add(crearVistaReportes(), "REPORTES");

        add(contentPanel, BorderLayout.CENTER);
        cardLayout.show(contentPanel, "USUARIO");
    }

    private JPanel crearVistaUsuario() {
        JPanel p = crearPanelBase();

        JPanel form = new JPanel(new GridLayout(3, 4, 10, 10));
        form.setBackground(BG_COLOR);

        JTextField txtId = new JTextField();
        JTextField txtNom = new JTextField();
        JTextField txtDir = new JTextField();
        JTextField txtToma = new JTextField();
        JComboBox<String> cbEstado = new JComboBox<>(new String[]{"Activo", "Inactivo"});

        JButton btnAdd = new JButton("Guardar Usuario");
        configurarBotonAccion(btnAdd, PRIMARY_COLOR);

        form.add(new JLabel("ID:"));
        form.add(txtId);

        form.add(new JLabel("Nombre:"));
        form.add(txtNom);

        form.add(new JLabel("Dirección:"));
        form.add(txtDir);

        form.add(new JLabel("Número de Toma:"));
        form.add(txtToma);

        form.add(new JLabel("Estado:"));
        form.add(cbEstado);

        form.add(new JLabel(""));
        form.add(btnAdd);

        btnAdd.addActionListener(e -> {
            String nuevoId = txtId.getText().trim();

            if (nuevoId.isEmpty() || txtNom.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Campos incompletos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (existeId(nuevoId)) {
                JOptionPane.showMessageDialog(this, "El ID '" + nuevoId + "' ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            modeloUsuarios.addRow(new Object[]{
                    nuevoId,
                    txtNom.getText(),
                    txtDir.getText(),
                    txtToma.getText(),
                    cbEstado.getSelectedItem()
            });

            actualizarComboIds();

            txtId.setText("");
            txtNom.setText("");
            txtDir.setText("");
            txtToma.setText("");

            JOptionPane.showMessageDialog(this, "Usuario registrado correctamente.");
        });

        p.add(form, BorderLayout.NORTH);
        p.add(new JScrollPane(new JTable(modeloUsuarios)), BorderLayout.CENTER);

        return p;
    }

    private JPanel crearVistaPago() {
        JPanel p = crearPanelBase();

        JPanel form = new JPanel(new GridLayout(3, 4, 10, 10));
        form.setBackground(BG_COLOR);

        JTextField txtAnio = new JTextField(String.valueOf(LocalDate.now().getYear()));
        JTextField txtMon = new JTextField();

        JButton btnAdd = new JButton("Guardar Pago");
        configurarBotonAccion(btnAdd, new Color(46, 204, 113));

        form.add(new JLabel("ID Usuario:"));
        form.add(cbIdsDisponibles);

        form.add(new JLabel("Mes:"));
        form.add(cbMeses);

        form.add(new JLabel("Año:"));
        form.add(txtAnio);

        form.add(new JLabel("Monto:"));
        form.add(txtMon);

        form.add(new JLabel(""));
        form.add(btnAdd);

        btnAdd.addActionListener(e -> {
            String idSel = (String) cbIdsDisponibles.getSelectedItem();
            String mesSel = (String) cbMeses.getSelectedItem();

            if (idSel == null || txtMon.getText().trim().isEmpty() || txtAnio.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Datos incompletos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                double monto = Double.parseDouble(txtMon.getText().trim());
                String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                modeloPagos.addRow(new Object[]{
                        idSel,
                        mesSel,
                        txtAnio.getText(),
                        monto,
                        fecha
                });

                txtMon.setText("");
                JOptionPane.showMessageDialog(this, "Pago registrado correctamente.");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Monto inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        p.add(form, BorderLayout.NORTH);
        p.add(new JScrollPane(new JTable(modeloPagos)), BorderLayout.CENTER);

        return p;
    }

    private JPanel crearVistaMorosos() {
        JPanel p = crearPanelBase();

        DefaultTableModel modMorosos = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Estado"}, 0
        );

        JButton btnConsultar = new JButton("Consultar morosos");
        configurarBotonAccion(btnConsultar, new Color(231, 76, 60));

        btnConsultar.addActionListener(e -> {
            modMorosos.setRowCount(0);

            Set<String> pagaron = new HashSet<>();

            for (int i = 0; i < modeloPagos.getRowCount(); i++) {
                pagaron.add(modeloPagos.getValueAt(i, 0).toString());
            }

            boolean hayMorosos = false;

            for (int i = 0; i < modeloUsuarios.getRowCount(); i++) {
                String id = modeloUsuarios.getValueAt(i, 0).toString();

                if (!pagaron.contains(id)) {
                    modMorosos.addRow(new Object[]{
                            id,
                            modeloUsuarios.getValueAt(i, 1),
                            modeloUsuarios.getValueAt(i, 4)
                    });
                    hayMorosos = true;
                }
            }

            if (!hayMorosos) {
                JOptionPane.showMessageDialog(this, "No hay usuarios morosos.");
            }
        });

        p.add(btnConsultar, BorderLayout.NORTH);
        p.add(new JScrollPane(new JTable(modMorosos)), BorderLayout.CENTER);

        return p;
    }

    private JPanel crearVistaReportes() {
        JPanel p = crearPanelBase();
        p.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 15, 10, 15);

        JButton b1 = new JButton("Reporte usuarios");
        JButton b2 = new JButton("Reporte pagos");
        JButton b3 = new JButton("Reporte morosos");

        Dimension tamBoton = new Dimension(220, 80);

        b1.setPreferredSize(tamBoton);
        b2.setPreferredSize(tamBoton);
        b3.setPreferredSize(tamBoton);

        configurarBotonAccion(b1, PRIMARY_COLOR);
        configurarBotonAccion(b2, PRIMARY_COLOR);
        configurarBotonAccion(b3, PRIMARY_COLOR);

        b1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b3.setFont(new Font("Segoe UI", Font.BOLD, 14));

        b1.addActionListener(e -> {
            if (modeloUsuarios.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No hay usuarios registrados.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Generando reporte de " + modeloUsuarios.getRowCount() + " usuarios...");
            }
        });

        b2.addActionListener(e -> {
            if (modeloPagos.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No hay pagos registrados.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Generando reporte de pagos...");
            }
        });

        b3.addActionListener(e -> {
            Set<String> pagaron = new HashSet<>();

            for (int i = 0; i < modeloPagos.getRowCount(); i++) {
                pagaron.add(modeloPagos.getValueAt(i, 0).toString());
            }

            boolean hayMorosos = false;

            for (int i = 0; i < modeloUsuarios.getRowCount(); i++) {
                String id = modeloUsuarios.getValueAt(i, 0).toString();

                if (!pagaron.contains(id)) {
                    hayMorosos = true;
                    break;
                }
            }

            if (!hayMorosos) {
                JOptionPane.showMessageDialog(this, "No hay usuarios morosos.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Generando reporte de morosos detectados...");
                cardLayout.show(contentPanel, "MOROSOS");
            }
        });

        c.gridx = 0;
        p.add(b1, c);

        c.gridx = 1;
        p.add(b2, c);

        c.gridx = 2;
        p.add(b3, c);

        return p;
    }

    private JPanel crearVistaTablaGenerica(String titulo, DefaultTableModel modelo) {
        JPanel p = crearPanelBase();

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));

        p.add(lblTitulo, BorderLayout.NORTH);
        p.add(new JScrollPane(new JTable(modelo)), BorderLayout.CENTER);

        return p;
    }

    private JPanel crearPanelBase() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBackground(BG_COLOR);
        p.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        return p;
    }

    private boolean existeId(String idBusqueda) {
        for (int i = 0; i < modeloUsuarios.getRowCount(); i++) {
            if (modeloUsuarios.getValueAt(i, 0).toString().equalsIgnoreCase(idBusqueda)) {
                return true;
            }
        }
        return false;
    }

    private void actualizarComboIds() {
        cbIdsDisponibles.removeAllItems();

        for (int i = 0; i < modeloUsuarios.getRowCount(); i++) {
            cbIdsDisponibles.addItem(modeloUsuarios.getValueAt(i, 0).toString());
        }
    }

    private JButton crearBotonMenu(String texto, String destino) {
        JButton b = new JButton(texto);

        b.setPreferredSize(new Dimension(220, 45));
        b.setBackground(new Color(44, 62, 80));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));

        b.addActionListener(e -> {
            actualizarComboIds();
            cardLayout.show(contentPanel, destino);
        });

        return b;
    }

    private void configurarBotonAccion(JButton b, Color c) {
        b.setBackground(c);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            SistemaAgua sistema = new SistemaAgua();
            new VentanaPrincipal(sistema).setVisible(true);
        });
    }
}