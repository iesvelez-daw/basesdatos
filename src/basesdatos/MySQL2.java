package basesdatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class MySQL2 {

    Connection conexion;
    boolean    conectada;
    String     bd;
    String     usuario;
    String     clave;
    String     url;

    public MySQL2() {
        this.conexion  = null;
        this.conectada = false;
        // Datos por defecto
        this.bd        = "BD";     
        this.usuario   = "root";
        this.clave     = "";
        this.url       = "jdbc:mysql://localhost/"+ this.bd;
    }

    public MySQL2(String bd, String usuario, String clave) {
        this.conexion  = null;
        this.conectada = false;
        this.bd        = bd;
        this.usuario   = usuario;
        this.clave     = clave;
        this.url       = "jdbc:mysql://localhost/" + this.bd;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        MySQL2 bd = new MySQL2();
        
        if (bd.conectar()) {
            // Realizamos consultas
            ResultSet nuevo  = bd.consultar("SELECT id, nombre, edad FROM personas;");
        } else {
            JOptionPane.showMessageDialog(null, "La conexión NO se ha realizado.");
        }
        bd.cerrar();
    }

    public boolean conectar() throws ClassNotFoundException {
        try {
            // Class.forName("com.mysql.jdbc.Driver");            
            conexion = DriverManager.getConnection(this.url, this.usuario, this.clave);
            return conectada = true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "La conexión NO se ha realizado. El error devuelto es: " + e.getMessage(),
                    "Información de error", JOptionPane.ERROR_MESSAGE);
            return conectada = false;
        }
    }

    public ResultSet consultar(String consulta) {
        ResultSet resultado = null;
        Statement sentencia;

        try {
            sentencia = this.conexion.createStatement();
            // Hacemos la consulta y almacenamos resultado
            resultado = sentencia.executeQuery(consulta);
            // Recorremos cada uno de los registros
            while (resultado.next()) {
                int    i  = resultado.getInt("id");
                String n  = resultado.getString("nombre");
                int    e  = resultado.getInt("edad");
                System.out.printf("%3d  %-20s  %3d\n", i, n, e);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "No se puede conectar a la BD. El error devuelto es: " + e.getMessage(),
                    "Información de error", JOptionPane.ERROR_MESSAGE);
        }
        return resultado;
    }

    public void cerrar() {
        try {
            if (conexion != null) 
                conexion.close();   
        }
        catch (SQLException e) { 
            JOptionPane.showMessageDialog(null,
                    "No se ha podido cerrar la BD. El error devuelto es: " + e.getMessage(),
                    "Error de desconexión", JOptionPane.ERROR_MESSAGE);
        }
     }
}
