package basesdatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class MySQL3 {

    Connection conexion;
    boolean    conectada;
    String     bd;
    String     usuario;
    String     clave;
    String     url;

    public MySQL3() {
        this.conexion  = null;
        this.conectada = false;
        // Datos por defecto
        this.bd        = "BD";     
        this.usuario   = "root";
        this.clave     = "";
        this.url       = "jdbc:mysql://localhost/"+ this.bd;
    }

    public MySQL3(String bd, String usuario, String clave) {
        this.conexion  = null;
        this.conectada = false;
        this.bd        = bd;
        this.usuario   = usuario;
        this.clave     = clave;
        this.url       = "jdbc:mysql://localhost/" + this.bd;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        String nombre;
        int    edad;
        int    numFilas;
        
        MySQL3 bd = new MySQL3();
        Scanner sc = new Scanner(System.in);
                
        System.out.println("Introduce datos para nueva persona:");
        System.out.println("Nombre: "); 
        nombre = sc.nextLine();
        
        System.out.println("Edad: "); 
        edad = sc.nextInt();
        
        if (bd.conectar()) {
            // Realizamos consultas
            numFilas = bd.modificar("INSERT INTO personas (id, nombre, edad) " 
                    + "VALUES (default, \"" + nombre +"\", " + edad + ");");
            System.out.println("La consulta ha afectado a " + numFilas + " filas.");
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

    public int modificar(String consulta) {
        int       numRegistros = 0;
        Statement sentencia;

        try {
            sentencia = this.conexion.createStatement();
            // Hacemos la consulta y almacenamos resultado
            numRegistros = sentencia.executeUpdate(consulta);
            return numRegistros;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return numRegistros;
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
