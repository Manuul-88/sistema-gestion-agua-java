package view;

import service.SistemaAgua;

import javax.swing.*;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal(SistemaAgua sistema) {
        setTitle("Sistema de Agua - San Baltazar");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setContentPane(new PanelConsultaDatos(sistema));
    }
}