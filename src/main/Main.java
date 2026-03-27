package main;

import service.SistemaAgua;
import view.VentanaPrincipal;

public class Main{
    public static void main (String[]args){
        SistemaAgua sistema = new SistemaAgua();
        VentanaPrincipal ventana = new VentanaPrincipal(sistema);
        ventana.setVisible(true);
    }
}
