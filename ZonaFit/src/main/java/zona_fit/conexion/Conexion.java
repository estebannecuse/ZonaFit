package zona_fit.conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    public static Connection getConnection(){
        Connection connection = null;
        var baseDatos = "zona_fit_db";
        var url = "jdbc:mysql://localhost:3306/" + baseDatos;
        var usuario = "root";
        var password = "Ferrari22!";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver"); // clase de conexion a DB
            connection = DriverManager.getConnection(url,usuario,password);

        }catch(Exception e){
            System.out.println("Error al conectarnos a la DB" + e.getMessage());
        }
        return connection;
    }

    public static void main(String[] args) {
        var conexion = Conexion.getConnection();
        if(conexion != null)
            System.out.println("Conexion existosa" + conexion);
        else System.out.println("error al conectarse");
    }
}
