package utilities;

import java.sql.*;

public class dbconexion {
    static Connection conn = null;
    static Statement stmt = null;
    static String BD = "sistema_facturacion";
    static String user = "root";
    static String password = "";
    static String url = "jdbc:mysql://localhost:3306/" + BD;
    
    public static Connection enlace(Connection conn) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            
        } catch (Exception e) {
            System.err.println("Base de datos no encontrada");
        }
        
        return conn;
        
    }
    
    public static Statement sta(Statement st) throws SQLException {
        conn = enlace(conn);
        stmt = conn.createStatement();
        return stmt;
    }
    
}
