package view;
import service.SistemaAgua;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private JPanel contentPanel;
    private CardLayout cardLayout;
    
    private DefaultTableModel modeloUsuarios;
    private DefaultTableModel modeloPagos;
    private JComboBox<String> cbIdsDisponibles;
    // Nuevo ComboBox para los meses
    private JComboBox<String> cbMeses;

    private final Color PRIMARY_COLOR = new Color(41, 128, 185); 
    private final Color SIDEBAR_COLOR = new Color(33, 47, 61);   
    private final Color BG_COLOR = new Color(245, 245, 245);      

    private SistemaAgua sistema;

    public VentanaPrincipal(SistemaAgua sistema) {
        this.sistema = sistema;
        setTitle("Sistema de Gestión - Selección de Meses");
        setSize(1100, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        modeloUsuarios = new DefaultTableModel(new String[]{"ID", "Nombre", "Dirección"}, 0);
        modeloPagos = new DefaultTableModel(new String[]{"ID Usuario", "Mes", "Monto"}, 0);
        
        cbIdsDisponibles = new JComboBox<>();
        
        // Inicializamos el ComboBox de meses con los nombres fijos
        String[] meses = {
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", 
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        };
        cbMeses = new JComboBox<>(meses);

        JPanel sidebar = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        sidebar.setBackground(SIDEBAR_COLOR);
        sidebar.setPreferredSize(new Dimension(250, 700));

        sidebar.add(crearBotonMenu("Registrar Usuario", "USUARIO"));
        sidebar.add(crearBotonMenu("Registrar Pago", "PAGO"));

        add(sidebar, BorderLayout.WEST);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        
        contentPanel.add(crearVistaUsuario(), "USUARIO");
        contentPanel.add(crearVistaPago(), "PAGO");

        add(contentPanel, BorderLayout.CENTER);
        cardLayout.show(contentPanel, "USUARIO");
    }

    private JPanel crearVistaUsuario() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBackground(BG_COLOR);
        p.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JPanel form = new JPanel(new GridLayout(2, 4, 10, 10));
        form.setBackground(BG_COLOR);
        JTextField txtId = new JTextField();
        JTextField txtNom = new JTextField();
        JTextField txtDir = new JTextField();
        JButton btnAdd = new JButton("Guardar");
        configurarBotonAccion(btnAdd, PRIMARY_COLOR);

        form.add(new JLabel("ID:")); form.add(txtId);
        form.add(new JLabel("Nombre:")); form.add(txtNom);
        form.add(new JLabel("Dirección:")); form.add(txtDir);
        form.add(new JLabel("")); form.add(btnAdd);

        btnAdd.addActionListener(e -> {
            String nuevoId = txtId.getText().trim();
            if(nuevoId.isEmpty()){
                JOptionPane.showMessageDialog(this, "El ID no puede estar vacío.");
                return;
            }

            if (existeId(nuevoId)) {
                JOptionPane.showMessageDialog(this, "El ID '" + nuevoId + "' ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                modeloUsuarios.addRow(new Object[]{nuevoId, txtNom.getText(), txtDir.getText()});
                actualizarComboIds();
                txtId.setText(""); txtNom.setText(""); txtDir.setText("");
            }
        });

        p.add(form, BorderLayout.NORTH);
        p.add(new JScrollPane(new JTable(modeloUsuarios)), BorderLayout.CENTER);
        return p;
    }

    private boolean existeId(String idBusqueda) {
        for (int i = 0; i < modeloUsuarios.getRowCount(); i++) {
            if (modeloUsuarios.getValueAt(i, 0).toString().equalsIgnoreCase(idBusqueda)) return true;
        }
        return false;
    }

    private JPanel crearVistaPago() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBackground(BG_COLOR);
        p.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JPanel form = new JPanel(new GridLayout(2, 4, 10, 10));
        form.setBackground(BG_COLOR);
        
        // Ahora usamos cbMeses en lugar de un JTextField
        JTextField txtMon = new JTextField();
        JButton btnAdd = new JButton("Guardar Pago");
        configurarBotonAccion(btnAdd, new Color(46, 204, 113));

        form.add(new JLabel("ID Usuario:")); form.add(cbIdsDisponibles);
        form.add(new JLabel("Mes:")); form.add(cbMeses);
        form.add(new JLabel("Monto:")); form.add(txtMon);
        form.add(new JLabel("")); form.add(btnAdd);

        btnAdd.addActionListener(e -> {
            String idSel = (String) cbIdsDisponibles.getSelectedItem();
            String mesSel = (String) cbMeses.getSelectedItem();
            
            if(idSel != null && !txtMon.getText().isEmpty()){
                modeloPagos.addRow(new Object[]{idSel, mesSel, txtMon.getText()});
                txtMon.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Datos incompletos.");
            }
        });

        p.add(form, BorderLayout.NORTH);
        p.add(new JScrollPane(new JTable(modeloPagos)), BorderLayout.CENTER);
        return p;
    }

    private void actualizarComboIds() {
        cbIdsDisponibles.removeAllItems();
        for (int i = 0; i < modeloUsuarios.getRowCount(); i++) {
            cbIdsDisponibles.addItem(modeloUsuarios.getValueAt(i, 0).toString());
        }
    }

    private JButton crearBotonMenu(String t, String destino) {
        JButton b = new JButton(t);
        b.setPreferredSize(new Dimension(220, 45));
        b.setBackground(new Color(44, 62, 80));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
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
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> {
         SistemaAgua sistema = new SistemaAgua();
         new VentanaPrincipal(sistema).setVisible(true);
});
    }
}