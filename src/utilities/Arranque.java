package utilities;

import controlador.controladorFactura;
import modelo.modeloFactura;
import vista.vistaFactura;

public class Arranque {
    public static void main(String[] args) {
        vistaFactura vista = new vistaFactura();
        modeloFactura modelo = new modeloFactura(vista);
        controladorFactura controlador = new controladorFactura(vista, modelo);
        
        modelo.inicio();
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    }
    
}
