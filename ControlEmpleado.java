
package Prueba1Archivos;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class ControlEmpleado {
    static private EmpleadoManager empresa;
    static Scanner lea = new Scanner(System.in);

    
    public static void main(String[] args) {
        int opcion = 0;
        do {
            System.out.println("\nMENU\n");
            System.out.println("1. Agregar Empleado");
            System.out.println("2. Listar Empleados No Despedidos");
            System.out.println("3. Despedir Empleado");
            System.out.println("4. Buscar Empleado activo");
            System.out.println("5. Salir");
            System.out.println("Escoja una opcion: ");

            try {
                opcion = lea.nextInt();
                switch (opcion) {
                    case 1:
                        agregarEmpleado();
                        break;
                    case 2:
                        listarEmpleados();
                        break;
                    case 3:
                        despedir(1);
                        break;
                    case 4:
                        buscar();
                        break;
                    default:
                        System.out.println("Opción no válida, por favor ingrese una opción entre 1 y 5.");
                }

            } catch (InputMismatchException e) {
                lea.nextLine();
                System.out.println("Por favor ingrese una opción correcta.");
            } catch (NullPointerException e) {
                System.out.println("");
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (opcion != 5);
    }

    private static void agregarEmpleado() throws IOException{
        System.out.println("Ingrese Nombre: ");
        String nombre = lea.next();
        System.out.println("Ingrese Monto: ");
        double monto = lea.nextDouble();
        empresa.addEmployee(nombre, monto);
    }
    
    private static void listarEmpleados() throws IOException{
        empresa.imprimirEmpleados();
    }
    private static void despedir(int code) throws IOException{
        System.out.println("Ingrese codigo: ");
        int codigo = lea.nextInt();
       empresa.despedirEmpleado(codigo);
        
    }
    private static void buscar() throws IOException{
        
        
    }
        
    
    
}
