package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import modelo.modeloFactura;
import vista.vistaFactura;

public class controladorFactura implements ActionListener, KeyListener {

    vistaFactura vista;
    modeloFactura modelo;

    public controladorFactura(vistaFactura vista, modeloFactura modelo) {
        this.vista = vista;
        this.modelo = modelo;
        
        vista.btnNuevo.addActionListener(this);
        vista.btnBuscarCliente.addActionListener(this);
        vista.txtBuscadorCliente.addKeyListener(this);
        vista.txtcodProduct.addActionListener(this);
        vista.btnaceptarclient.addActionListener(this);
        vista.txtfiltro.addKeyListener(this);
        vista.btnAceptarProd.addActionListener(this);
        vista.btnADD.addActionListener(this);
        vista.btnDELETE.addActionListener(this);
        vista.txtNumFactura.addActionListener(this);
        vista.txtNumFecha.addActionListener(this);
        vista.rbnContado.addActionListener(this);
        vista.rbnContado.addKeyListener(this);
        vista.rbnCredito.addActionListener(this);
        vista.txtCantProducto.addActionListener(this);
        vista.btnGuardar.addActionListener(this);
        
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object p = e.getSource();
        if (p.equals(vista.btnNuevo)) {
            modelo.bloquearObjetos(true);
            modelo.activarBotones(false, true, false, false, false);
            modelo.generarCodigo();
            vista.txtNumFactura.requestFocus();
        }
        
        if (p.equals(vista.btnBuscarCliente)) {
            modelo.llamarBuscadorCliente();
            vista.txtBuscadorCliente.requestFocus();
            modelo.cargarTablaCliente("");
        }
        
        if(p.equals(vista.txtcodProduct)) {
            
            if (vista.txtcodProduct.getText().trim().length() == 0){
                modelo.llamarBuscadorProductos();
                modelo.cargarTablaProducto("");
            } else {
                modelo.buscarProductoxCodigo(vista.txtcodProduct.getText());
            }
            modelo.buscarProductoxCodigo(vista.txtcodProduct.getText());
        }
        
        if(p.equals(vista.btnaceptarclient)){
            modelo.transferirClientes();
        }
        
        if (p.equals(vista.btnAceptarProd)) {
            modelo.transferirProductos();
        }
        
        if (p.equals(vista.btnADD)) {
            modelo.añadirProductos(vista.txtcodProduct.getText());
            modelo.totales();
            vista.txtcodProduct.setText("");
            vista.txtProducto.setText("");
            vista.txtCantProducto.setText("");
            vista.txtcodProduct.requestFocus();
        }
        
        if (p.equals(vista.btnDELETE)) {
            modelo.eliminarProducto();
            modelo.totales();
        }
        
        if (vista.txtNumFactura.equals(p)) {
            vista.txtNumFecha.requestFocus();
        }
        
        if (vista.txtNumFecha.equals(p)){
            vista.rbnContado.requestFocus();
        }
        
        if (p.equals(vista.rbnContado)) {
            vista.btnBuscarCliente.requestFocus();
        }
        
        if (p.equals(vista.rbnCredito)){
            vista.btnBuscarCliente.requestFocus();
        }
        
        if (p.equals(vista.txtCantProducto)) {
            vista.btnADD.requestFocus();
        }
        
        if (p.equals(vista.btnGuardar)) {
            modelo.setId(vista.txtcodFactura.getText());
            modelo.setCodCliente(vista.txtcodigo_cli.getText());
            modelo.setUsuario(vista.txtcodigousu.getText());
            String condicion;
            if (vista.rbnContado.isSelected()) {
                condicion = "contado";
            } else {
                condicion = "credito";
            }
            modelo.setCondicion(condicion);
            modelo.setFecha(vista.txtNumFecha.getText());
            modelo.setNumero(vista.txtNumFactura.getText());
            if (vista.txtestado.getText().equals("PENDIENTE")) {
                modelo.setEstado("P");
            }
            
            modelo.guardarFactura();
            modelo.cargarDetalleFactura();
            //modelo.aumentarStock();
        }
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        Object p = e.getSource();
        
        if (p.equals(vista.rbnContado)) {
            vista.rbnCredito.requestFocus();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Object p = e.getSource();
        if (p.equals(vista.txtBuscadorCliente)) {
            modelo.cargarTablaCliente(vista.txtBuscadorCliente.getText());
        }
        
        if (p.equals(vista.txtfiltro)) {
            modelo.cargarTablaProducto(vista.txtfiltro.getText());
        }
    }
}
