package zona_fit.datos;

import zona_fit.conexion.Conexion;
import zona_fit.dominio.Cliente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

public class ClienteDAO implements IclienteDAO{
    @Override
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        PreparedStatement ps; // este objeto me permite preparar la sentencia de sql.
        ResultSet rs; // este objeto nos permite recibir el resultado de la consulta
        Connection con = Conexion.getConnection();
        var sql = "SELECT * FROM cliente ORDER BY id";
        try{
            ps = con.prepareStatement(sql); // este metodo devuelve un objeto de tipo PS
            rs = ps.executeQuery(); // ejecuto la sentencia;
            while(rs.next()){ // .next se posiciona en el primer registro a iterar, devuelve false si no hay mas
                var cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membresia"));
                clientes.add(cliente);
            }
        }catch(Exception e){
            System.out.println("Error al listar clientes" + e.getMessage());
        }
        finally{
            try{
                con.close();
            }catch(Exception e){
                System.out.println("Error al cerrar la conexion" + e.getMessage());
            }

        }
        return clientes;
    }

    @Override
    public boolean buscarClientePorId(Cliente cliente) {
        PreparedStatement ps; // este objeto me permite preparar la sentencia de sql.
        ResultSet rs;
        Connection con = Conexion.getConnection();
        var sql = "SELECT * FROM cliente WHERE id = ?";
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, cliente.getId()); // este parametro se inyecta en el "?" del string SQL, es un parametro
            rs = ps.executeQuery();
            if(rs.next()){
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membresia"));
                return true;
            }
        }catch(Exception e){
            System.out.println("Error al recuperar cliente por ID:" + e.getMessage());
        }finally{
            try{
                con.close();
            }catch(Exception e ){
                System.out.println("error al finalizar la conexion" + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean agregarCliente(Cliente cliente) {
        return false;
    }

    @Override
    public boolean modificarCliente(Cliente cliente) {
        return false;
    }

    @Override
    public boolean eliminarCliente(Cliente cliente) {
        return false;
    }

    public static void main(String[] args) {
        // prueba listar clientes
        System.out.println("** Listar Clientes ***");
        IclienteDAO clienteDao = new ClienteDAO();
        var clientes = clienteDao.listarClientes();
        clientes.forEach(System.out :: println);
    }
}
