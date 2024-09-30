package zona_fit.presentacion;

import zona_fit.conexion.Conexion;
import zona_fit.datos.ClienteDAO;
import zona_fit.datos.IclienteDAO;
import zona_fit.dominio.Cliente;

import java.sql.SQLOutput;
import java.util.Scanner;

public class ZonaFitAPp {
    public static void main(String[] args) {
        zonaFitApp();
    }

    private static void zonaFitApp(){
        var salir = false;
        var consola = new Scanner(System.in);
        IclienteDAO clienteDAO = new ClienteDAO();
        System.out.println("********* Bienvenido a ZonaFit ********");

        while(!salir){
            try{
                var opcion = mostrarMenu(consola);
                salir = ejecutarOpciones(consola, opcion, clienteDAO);
            }catch(Exception e){
                System.out.println("Ocurrio un error" + e.getMessage());
            }
            System.out.println();
        }

    }

    private static int mostrarMenu(Scanner consola){
        System.out.print("""
                Menu:
                1. Listar Clientes.
                2. Buscar Cliente
                3. Agregar Cliente
                4. Modificar Cliente
                5. Eliminar Cliente
                6. Salir
                Elige una opcion: \s""");
        // leemos y retornamos la opcion seleccionada por el usuario
        return Integer.parseInt(consola.nextLine());
    }

    private static boolean ejecutarOpciones(Scanner consola, int opcion, IclienteDAO clienteDAO){
        var salir = false;
        switch (opcion){
            case 1 -> listarClientes(clienteDAO);
            case 2 -> buscarCliente(clienteDAO, consola);
            case 3 -> agregarCliente(clienteDAO, consola);
            case 4 -> modificarCliente(clienteDAO, consola);
            case 5 -> eliminarCliente(clienteDAO, consola);
            case 6 -> {
                System.out.println("Regresa pronto");
                salir = true;
            }
            default -> System.out.println("Opcion invalida");
        }
        return salir;
    }

    private static void listarClientes(IclienteDAO clienteDAO){
        System.out.println("*** Listado de Clientes ");
        var clientes = clienteDAO.listarClientes();
        clientes.forEach(System.out::println);
    }

    private static void buscarCliente(IclienteDAO clienteDAO, Scanner consola){
        System.out.println("Introduce el id del cliente ");
        var id = Integer.parseInt(consola.nextLine());
        var cliente = new Cliente(id);
        var clienteEncontrado = clienteDAO.buscarClientePorId(cliente);
        if(clienteEncontrado){
            System.out.println("Cliente encontrado con exito !  " + cliente);
        }else{
            System.out.println("Cliente no encontrado" + cliente);
        }
    }

    private static void agregarCliente(IclienteDAO clienteDAO, Scanner consola){
        System.out.println("--- Agregar Cliente ---");
        System.out.println("Ingrese el nombre del Usuario: ");
        String nombre = consola.nextLine();
        System.out.println("Ingrese el apellido del Usuario: ");
        String apellido = consola.nextLine();
        System.out.println("Ingrese la membresia del Usuario: ");
        var membresia = Integer.parseInt(consola.nextLine());
        var cliente = clienteDAO.agregarCliente(new Cliente(nombre,apellido,membresia));
        if(cliente)
            System.out.println("Cliente agregado con exito");
        else
            System.out.println("Error al agregar cliente");
    }

    private static void modificarCliente(IclienteDAO clienteDAO, Scanner consola){
        System.out.println("** MOdificar Cliente **");
        System.out.println("Ingrese el ID del cliente a modificar");
        var id = Integer.parseInt(consola.nextLine());
        System.out.println("Ingrese el nombre del Usuario: ");
        String nombre = consola.nextLine();
        System.out.println("Ingrese el apellido del Usuario: ");
        String apellido = consola.nextLine();
        System.out.println("Ingrese la membresia del Usuario: ");
        var membresia = Integer.parseInt(consola.nextLine());
        var clienteModificado = clienteDAO.modificarCliente(new Cliente(id,nombre,apellido,membresia));
        if(clienteModificado)
            System.out.println("Cliente modificado con exito");
        else
            System.out.println("Error al modificar el cliente");
    }

    private static void eliminarCliente(IclienteDAO clienteDAO, Scanner consola){
        System.out.println("*** Eliminar Cliente ***");
        System.out.println("Ingrese el ID del cliente a eliminar");
        int id = Integer.parseInt(consola.nextLine());
        var clienteEliminado = clienteDAO.eliminarCliente(new Cliente(id));
        if(clienteEliminado)
            System.out.println("Cliente eliminado con exito");
        else
            System.out.println("Error al eliminar cliente");
    }

}
