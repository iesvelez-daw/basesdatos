package basesdatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {

    Connection conexion;
    boolean    conectada;
    String     bd;
    String     usuario;
    String     clave;
    String     url;

    public MySQL() {
        this.conexion  = null;
        this.conectada = false;
        // Datos por defecto
        this.bd        = "BD";     
        this.usuario   = "root";
        this.clave     = "";
        this.url       = "jdbc:mysql://localhost/"+ this.bd;
    }

    public MySQL(String bd, String usuario, String clave) {
        this.conexion  = null;
        this.conectada = false;
        this.bd        = bd;
        this.usuario   = usuario;
        this.clave     = clave;
        this.url       = "jdbc:mysql://localhost/" + this.bd;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        MySQL bd = new MySQL();
        
        if (bd.conectar()) {
            // Realizamos consultas
            ResultSet nuevo  = bd.consultar("SELECT id, nombre, edad FROM personas;");
        } else {
            System.out.println("La conexión NO se ha realizado");
        }
        bd.cerrar();
    }

    public boolean conectar() throws ClassNotFoundException {
        try {
            // Class.forName("com.mysql.jdbc.Driver");            
            conexion = DriverManager.getConnection(this.url, this.usuario, this.clave);
            return conectada = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
        }
        return resultado;
    }

    public void cerrar() {
        try {
            if (conexion != null) 
                conexion.close();   
        }
        catch (SQLException e) { 
            System.out.println(e.getMessage()); 
        }
     }
}
