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
       PreparedStatement ps;
       Connection con = Conexion.getConnection();
       //como solo voy a hacer una query, no voy a manejar un resultado, no preciso RS
       String sql = "INSERT into cliente(nombre, apellido, membresia) " + "VALUES(?, ?, ?)";
       try{
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setInt(3,cliente.getMembresia());
            ps.execute();
            return true;
       }catch(Exception e){
           System.out.println("Error al agregar cliente " + e.getMessage());
        }finally{
           try{
               con.close();
           }catch(Exception e){
               System.out.println("Error al cerrar la conexion" + e.getMessage());
           }
       }

        return false;
    }

    @Override
    public boolean modificarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = Conexion.getConnection();
        String sql = "UPDATE cliente SET nombre=?, apellido=?, membresia=? "+ " WHERE id=?";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2,cliente.getApellido());
            ps.setInt(3, cliente.getMembresia());
            ps.setInt(4,cliente.getId());
            ps.execute();
            return true;
        }catch (Exception e){
            System.out.println("Error al modificar cliente " + e.getMessage());
        }finally{
            try{
                con.close();
            }catch(Exception e){
                System.out.println("Error al cerrar la conexion" + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean eliminarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = Conexion.getConnection();
        String sql = "DELETE FROM cliente WHERE id=?";
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, cliente.getId());
            ps.execute();
            return true;
        }catch(Exception e){
            System.out.println("error al eliminar cliente" + e.getMessage());
        }finally{
            try{
                con.close();
            }catch(Exception e){
                System.out.println("No se pudo cerrar la conexion" + e.getMessage());
            }
        }

        return false;
    }

    public static void main(String[] args) {
        // prueba listar clientes
//        System.out.println("** Listar Clientes ***");
        IclienteDAO clienteDao = new ClienteDAO();
//        var clientes = clienteDao.listarClientes();
//        clientes.forEach(System.out :: println);


        // prueba buscar por id
        var cliente1 = new Cliente(2);
        System.out.println("Cliente antes de la busqueda" + cliente1);
        var encontrado = clienteDao.buscarClientePorId(cliente1);
        if(encontrado){
            System.out.println("Cliente encontrado " + cliente1);
        }else{
            System.out.println(" no se encontro cliente con id: " + cliente1.getId());
        }


        //prueba agregar cliente

//        var nuevoCliente = new Cliente("esteban", "Necuse", 400);
//        var agregado = clienteDao.agregarCliente(nuevoCliente);
//        if(agregado){
//            System.out.println("Cliente agregado con exito: " + nuevoCliente);
//        }else {
//            System.out.println("No se pudo agregar el cliente");
//        }
        // modificar cliente

        var modificarCliente = new Cliente(3, "Esteban Daniel", "Necuse", 400);
        var modificado = clienteDao.modificarCliente(modificarCliente);
        if(modificado){
            System.out.println("cliente modificado " + modificado);
        }else{
            System.out.println("No se modifico");
        }


        // Eliminar cliente
        var clienteEliminar = new Cliente(2);
        var eliminado = clienteDao.eliminarCliente(clienteEliminar);
        if(eliminado)
            System.out.println("Cliente eliminado" + clienteEliminar);
        else
            System.out.println("No se pudo eliminar el cliente " + clienteEliminar);

        //Listop de nuevo
        System.out.println("** Listar Clientes ***");
        var clientes = clienteDao.listarClientes();
        clientes.forEach(System.out :: println);

    }
}
