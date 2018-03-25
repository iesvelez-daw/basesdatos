package basesdatos;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class ODBC {

    JTextField sqlTexto = new JTextField(80);
    JButton boton1 = new JButton("Ejecuta SQL");
    JTextArea areaResultados = new JTextArea(15, 80);
    JFrame marco;

    public ODBC() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        marco = new JFrame("PrimeraBaseDatos");
        marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        marco.setSize(450, 350);
        marco.setLayout(null);
        marco.setIconImage(Toolkit.getDefaultToolkit().createImage("Libros.gif"));

        boton1.addActionListener(new GestionaBotones());
        sqlTexto.setBounds(10, 30, 300, 25);
        areaResultados.setBounds(10, 100, 400, 200);
        boton1.setBounds(10, 60, 150, 25);
        marco.add(sqlTexto);
        marco.add(areaResultados);
        marco.add(boton1);
        marco.setVisible(true);
    }

    void verBaseDatos() {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection conector = DriverManager.getConnection("jdbc:odbc:Bibliografia");
            Statement sentencia = conector.createStatement();
            boolean tieneResultados = sentencia.execute(sqlTexto.getText());
            if (tieneResultados) {
                ResultSet resultado = sentencia.getResultSet();
                if (resultado != null) {
                    muestraResultados(resultado);
                }
            } else {
                areaResultados.setText("");
            }
            conector.close();

        } catch (ClassNotFoundException e) {
            System.out.println("Driver no encontrado");
        } catch (Exception e) {
            System.out.println("Error en la conexión: ");
            e.printStackTrace(System.out);
        }
    }

    void muestraResultados(ResultSet r) throws SQLException {
        ResultSetMetaData rSMD = r.getMetaData();
        int numeroCampos = rSMD.getColumnCount();
        String texto = "";

        for (int i = 1; i <= numeroCampos; ++i) {
            if (i < numeroCampos) {
                texto += rSMD.getColumnName(i) + " ";
            } else {
                texto += rSMD.getColumnName(i);
            }
        }

        texto += "\n";
        for (int i = 0; i < 80; i++) {
            texto += "-";
        }
        texto += "\n";

        while (r.next()) {
            for (int i = 1; i <= numeroCampos; ++i) {
                if (i < numeroCampos) {
                    texto += r.getString(i) + " ";
                } else {
                    texto += r.getString(i).trim();
                }
            }
            texto += "\n";
        }
        areaResultados.setText(texto);
    }

    class GestionaBotones implements ActionListener {

        public void actionPerformed(ActionEvent evento) {
            String texto = evento.getActionCommand();
            if (texto == "Ejecuta SQL") {
                verBaseDatos();
            }
        }
    }

    public static void main(String args[]) {
        ODBC app = new ODBC();
    }
}

