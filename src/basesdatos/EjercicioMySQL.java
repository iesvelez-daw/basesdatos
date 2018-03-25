package basesdatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class EjercicioMySQL {

    private static class MySQL {

        Connection conexion;
        String bd;
        String usuario;
        String clave;
        String url;

        public MySQL() {
            this.conexion = null;
            // Datos por defecto
            this.bd = "BD";
            this.usuario = "root";
            this.clave = "";
            this.url = "jdbc:mysql://localhost/" + this.bd;
        }

        public MySQL(String bd, String usuario, String clave) {
            this.conexion = null;
            this.bd = bd;
            this.usuario = usuario;
            this.clave = clave;
            this.url = "jdbc:mysql://localhost/" + this.bd;
        }

        public boolean conectar() throws ClassNotFoundException {
            try {
                conexion = DriverManager.getConnection(this.url, this.usuario, this.clave);
                return true;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }

        public ResultSet consultarBD(String consulta) {
            ResultSet resultado = null;

            try {
                resultado = conexion.createStatement().executeQuery(consulta);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return resultado;
        }

        public int modificarBD(String consulta) {
            int numRegistros = 0;

            try {
                numRegistros = conexion.createStatement().executeUpdate(consulta);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return numRegistros;
        }

        public void cerrar() {
            try {
                conexion.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {

        String consultaCREATETABLE = "create table profesores (\n"
                + "id INTEGER primary key auto_increment,\n"
                + "nombre VARCHAR(50),\n"
                + "apellidos VARCHAR(50),\n"
                + "especialidad VARCHAR(50),\n"
                + "telefono CHAR(9)\n"
                + " ); ";
        String consultaINSERT1 = "insert into profesores values (default, 'Ana', 'López', 'Lengua', '999111222')";
        String consultaINSERT2 = "insert into profesores values (default, 'Ramón', 'García', 'Matemáticas', '999222333')";
        String consultaINSERT3 = "insert into profesores values (default, 'Sara', 'Fernández', 'Inglés', '999333444')";        
        
        String consultaUPDATE =  "update profesores set especialidad = 'Historia' where especialidad = 'Lengua';";
        String consultaDELETE =  "delete from profesores where especialidad = 'Inglés';";    
        
        String consultaSELECT = "select id, nombre, apellidos, especialidad, telefono FROM profesores;";
        
        ResultSet registros;
        
        MySQL bd = new MySQL();
        try {
            if (bd.conectar()) {
                // Creamos tabla
                bd.modificarBD(consultaCREATETABLE);
                
                // Insertamos registros
                bd.modificarBD(consultaINSERT1);
                bd.modificarBD(consultaINSERT2);
                bd.modificarBD(consultaINSERT3);   
                
                // Modificamos registros
                bd.modificarBD(consultaUPDATE);  
                
                // Borramos registros
                bd.modificarBD(consultaDELETE);                  
                
                // Consultamos registros
                registros = bd.consultarBD(consultaSELECT);
                while (registros.next()) {
                    int    i = registros.getInt("id");
                    String n = registros.getString("nombre");
                    String a = registros.getString("apellidos");
                    String e = registros.getString("especialidad");
                    String t = registros.getString("telefono");
                    System.out.printf("%3d  %-20s  %-20s  %-20s  %9s\n", i, n, a, e, t);
                }
            } else {
                System.out.println("La conexión NO se ha realizado");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }

        bd.cerrar();

    }

}
