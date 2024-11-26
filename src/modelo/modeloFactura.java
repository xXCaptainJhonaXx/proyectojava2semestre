package modelo;

import java.sql.SQLException;
import vista.vistaFactura;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class modeloFactura {
    
    private String id;
    private String codCliente;
    private String usuario;
    private String condicion;
    private String fecha;
    private String numero;
    private String estado;
    
    Statement stmt;
    vistaFactura vista;
    ResultSet rs;
    DefaultTableModel modeloCliente;
    DefaultTableModel modeloProducto;
    DefaultTableModel modeloDetalle;

    public modeloFactura(vistaFactura vista) {
        this.vista = vista;
    }
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    //funcionalidades
    
    public void bloquearObjetos(boolean aux) {
        vista.txtNumFactura.setEnabled(aux);
        vista.txtNumFecha.setEnabled(aux);
        vista.rbnContado.setEnabled(aux);
        vista.rbnCredito.setEnabled(aux);
        vista.txtcodProduct.setEnabled(aux);
        vista.txtCantProducto.setEnabled(aux);
        vista.btnBuscarCliente.setEnabled(aux);
        vista.btnADD.setEnabled(aux);
        vista.btnDELETE.setEnabled(aux);
        
    }
    
    public void activarBotones(boolean n, boolean g, boolean i, boolean b, boolean a) {
        vista.btnNuevo.setEnabled(n);
        vista.btnGuardar.setEnabled(g);
        vista.btnImprimir.setEnabled(i);
        vista.btnBuscar.setEnabled(b);
        vista.btnAnular.setEnabled(a);
    }
    
    public void inicio() {
        bloquearObjetos(false);
        activarBotones(true, false, false, true, false);
        modeloCliente = (DefaultTableModel) vista.tablaCliente.getModel();
        modeloProducto = (DefaultTableModel) vista.tablaProductoBuscar.getModel();
        modeloDetalle = (DefaultTableModel) vista.TablaDetalleFac.getModel();
        
    }
    
    public void llamarBuscadorCliente() {
        vista.buscadorCliente.setSize(844, 537);
        vista.buscadorCliente.setLocationRelativeTo(vista);
        vista.buscadorCliente.setVisible(true);
    }
    
    public void cargarTablaCliente(String filtro) {
        String sql = "SELECT * FROM cliente WHERE apellido LIKE '"+filtro+"%'";
        try {
            modeloCliente.setRowCount(0);
            stmt = utilities.dbconexion.sta(stmt);
            rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                modeloCliente.addRow(new Object[]{
                    rs.getString("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("apellido2"),
                    rs.getString("CI")
                });
            }
            vista.tablaCliente.setModel(modeloCliente);
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(modeloFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void transferirClientes() {
        Integer fila = vista.tablaCliente.getSelectedRow();
        vista.txtcodigo_cli.setText(vista.tablaCliente.getValueAt(fila, 0).toString());
        vista.txtnombre_cli.setText(vista.tablaCliente.getValueAt(fila, 1).toString());
        vista.txtapellido_cli.setText(vista.tablaCliente.getValueAt(fila, 2).toString());
        vista.txtci_cli.setText(vista.tablaCliente.getValueAt(fila, 3).toString());
        
        vista.buscadorCliente.setVisible(false);
        vista.txtcodProduct.requestFocus();
        
    }
    
    public void buscarProductoxCodigo(String cod) {
        String sql = "SELECT * FROM producto WHERE id = '"+cod+"'";
        
        try {
            stmt = utilities.dbconexion.sta(stmt);
            rs = stmt.executeQuery(sql);
            rs.next();
            if (rs.getRow() == 0) {
                vista.txtProducto.setText("Producto no encontrado");
            } else {
                vista.txtProducto.setText(rs.getString("nombre"));
                vista.txtCantProducto.requestFocus();
            }
            stmt.close();
            rs.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(modeloFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void llamarBuscadorProductos() {
        vista.buscadorProducto.setSize(735, 551);
        vista.buscadorProducto.setLocationRelativeTo(vista);
        vista.buscadorProducto.setVisible(true);
        
        vista.txtfiltro.requestFocus();
    }
    
    public void cargarTablaProducto(String filtro) {
        String sql = "SELECT * FROM producto WHERE nombre LIKE '"+filtro+"%'";
        modeloProducto.setRowCount(0);
        try {
            stmt = utilities.dbconexion.sta(stmt);
            rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                modeloProducto.addRow(new Object[] {
                    rs.getString("id"),
                    rs.getString("nombre"),
                    rs.getString("precio"),
                    rs.getString("cantidad")
                });
            }
            vista.tablaProductoBuscar.setModel(modeloProducto);
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(modeloFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void transferirProductos() {
        Integer fila = vista.tablaProductoBuscar.getSelectedRow();
        
        vista.txtcodProduct.setText(vista.tablaProductoBuscar.getValueAt(fila, 0).toString());
        vista.txtProducto.setText(vista.tablaProductoBuscar.getValueAt(fila, 1).toString());
        
        vista.buscadorProducto.setVisible(false);
        vista.txtCantProducto.requestFocus();

    }
    
    public void añadirProductos(String codigo) {
        String sql = "SELECT * FROM producto WHERE id = '"+codigo+"'";
        
        try {
            stmt = utilities.dbconexion.sta(stmt);
            rs = stmt.executeQuery(sql);
            rs.next();
            if (rs.getRow() == 0) {
                JOptionPane.showMessageDialog(vista, "Producto no encontrado");
            } else {
                String iva = rs.getString("iva");
                
                
                if (iva.equals("13")) {
                    modeloDetalle.addRow(new Object[] {
                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getString("precio"),
                        
                        vista.txtCantProducto.getText(),
                        "",
                        Integer.parseInt(vista.txtCantProducto.getText())*Integer.parseInt(rs.getString("precio"))
                    });
                }
                
                if (iva.equals("0")) {
                    modeloDetalle.addRow(new Object[] {
                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getString("precio"),
                        
                        vista.txtCantProducto.getText(),
                        Integer.parseInt(vista.txtCantProducto.getText())*Integer.parseInt(rs.getString("precio")),
                        ""
                    });
                }
            }
            stmt.close();
            rs.close();
            vista.TablaDetalleFac.setModel(modeloDetalle);
            
        } catch (SQLException ex) {
            Logger.getLogger(modeloFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void eliminarProducto() {
        int fila = vista.TablaDetalleFac.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un producto a eliminar");
        } else {
            modeloDetalle.removeRow(fila);
        }
    }

    public void totales() {
        int cantFilas = vista.TablaDetalleFac.getRowCount();
        double sumaExenta = 0;
        double sumaIva = 0;
        double total = 0;

        for (int i = 0; i < cantFilas; i++) {
            // Obtener el precio y cantidad de cada fila
            int cantidad = Integer.parseInt(vista.TablaDetalleFac.getValueAt(i, 3).toString());
            double precio = Double.parseDouble(vista.TablaDetalleFac.getValueAt(i, 2).toString());

            // Calcular subtotal
            double subtotal = cantidad * precio;
            sumaExenta += subtotal;

            // Calcular IVA y sumarlo
            double iva = subtotal * 0.13; // 13% del subtotal
            sumaIva += iva;
        }

        // Total general
        total = sumaExenta + sumaIva;

        // Mostrar resultados en los campos de texto
        vista.txtExenta.setText(String.format("%.2f", sumaExenta));
        vista.txt13.setText(String.format("%.2f", sumaIva));
        vista.txttotal.setText(String.format("%.2f", total));
        
        vista.txtliq13.setText(String.format("%.2f", sumaIva));     // Monto del IVA (igual a txt13)
        vista.txtTotIVA.setText(String.format("%.2f", sumaIva));    // Total del IVA
    }
    
    public void generarCodigo() {
        String sql = "SELECT IFNULL(MAX(id), 0)+1 AS codigo FROM facturacion";
        
        try {
            stmt = utilities.dbconexion.sta(stmt);
            rs = stmt.executeQuery(sql);
            rs.next();
            vista.txtcodFactura.setText(rs.getString("codigo"));
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(modeloFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void guardarFactura() {
        String sql = "INSERT INTO facturacion(id, cliente_id, usuario_id, factura_condicion, factura_fecha, factura_numero, factura_estado) VALUES ('"+id+"', '"+codCliente+"', '"+usuario+"', '"+condicion+"', '"+fecha+"', '"+numero+"', '"+estado+"')";
        
        try {
            stmt = utilities.dbconexion.sta(stmt);
            stmt.executeUpdate(sql);
            stmt.close();
            JOptionPane.showMessageDialog(vista, "Guardado");
        } catch (SQLException ex) {
            Logger.getLogger(modeloFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cargarDetalleFactura(){
        String sql;
        Integer cantidadItem = vista.TablaDetalleFac.getRowCount();
        
        for (int i = 0; i < cantidadItem; i++){
            sql = "INSERT INTO detalle_facturacion(facturacion_id, producto_id, precio, cantidad) VALUES ('"+id+"', '"+vista.TablaDetalleFac.getValueAt(i, 0)+"', '"+vista.TablaDetalleFac.getValueAt(i, 3)+",'"+vista.TablaDetalleFac.getValueAt(i, 2)+"')";
            try {
                stmt = utilities.dbconexion.sta(stmt);
            } catch (SQLException ex) {
                Logger.getLogger(modeloFactura.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        JOptionPane.showMessageDialog(vista, "Detalle Guardado");
    }
    
    /*public void aumentarStock() {
        String sql;
        
        Integer cantidadItem = vista.TablaDetalleFac.getRowCount();
        
        for (int i = 0; i < cantidadItem; i++) {
            sql = "UPDATE producto SET cantidad = cantidad +'"+vista.TablaDetalleFac.getValueAt(i, 3)+"' WHERE id = '"+vista.TablaDetalleFac.getValueAt(i, 0)+"'";
            
            try {
                stmt = utilities.dbconexion.sta(stmt);
                stmt.executeUpdate(sql);
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(modeloFactura.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }*/
   
}
