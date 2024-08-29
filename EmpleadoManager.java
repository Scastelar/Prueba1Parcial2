
package Prueba1Archivos;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.Date;

public class EmpleadoManager {
    private RandomAccessFile rcods, remps;
    
    public EmpleadoManager(){
        try{
          //1- Asegurar que el folder de Company exista
          File mf = new File("company");
          mf.mkdir();
          //2- Instanciar los RAFs dentro del folder company
          rcods = new RandomAccessFile("company/codigo.emp","rw");
          remps = new RandomAccessFile("company/empleados.emp","rw");
          //3- Inicializar el archivo de codigos, si es nuevo
          initCode();
        } catch(IOException e){
            System.out.println("Error");
        }
    }
    
    /*
    Formato Codigos.emp 
    int code
    */
    private void initCode() throws IOException{
        //Cotejar el tamaño del archivo
        if (rcods.length()==0){
            rcods.writeInt(1);
        }
    }
    
    /*
    Crear la funcion getCode, para generar
    el codigo siguiente e indicarme cual es el codigo 
    actual
    */
    private int getCode() throws IOException{
        rcods.seek(0);
        int codigo = rcods.readInt();
        rcods.seek(0);
        rcods.writeInt(codigo+1);       //Reescribe el codigo dentro del archivo
        return codigo;                  //retorna el codigo original
    }
    
    /*
    Formato Empleados.emp
    
    int code
    String name
    double salary
    long fechaContratacion
    long fechaDespido
    
    */
    
    public void addEmployee(String name, double monto)throws IOException{
        remps.seek(remps.length());
        //Code
        int code = getCode();
        remps.writeInt(code);
        //String
        remps.writeUTF(name);
        //Salary
        remps.writeDouble(monto);
        //Fecha de Contratacion
        remps.writeLong(Calendar.getInstance().getTimeInMillis());
        //Fecha Despido
        remps.writeLong(0);
        //Crear carpeta y archivo individual de cada empleado
        createEmployeeFolder(code);
        
    }
    
    private String employeeFolder(int code){
       return "company/empleado" + code;
    }
    private void createEmployeeFolder(int code) throws IOException{
       File edir = new File(employeeFolder(code));
       edir.mkdir();
       //Crear archivo del empleado
       this.createYearSalesFileFor(code);
    }
    
    private RandomAccessFile salesFileFor(int code)throws IOException{
        String dirPadre = employeeFolder(code);
        int yearActual = Calendar.getInstance().get(Calendar.YEAR);
        String path = dirPadre + "/ventas" + yearActual + ".emp";
        return new RandomAccessFile(path,"rw");
    }
    
    /*
    Formato VentasYear.emp
    double ventas
    boolean estadoPagar
    */
    private void createYearSalesFileFor(int code) throws IOException{
       RandomAccessFile raf = salesFileFor(code);
       if (raf.length()==0){
           for(int mes=0;mes<12;mes++){
            raf.writeDouble(0);
            raf.writeBoolean(false);   
           }
       }
    }
    
    public String imprimirEmpleados() throws IOException{
        remps.seek(0);
        return "****LISTA DE EMPLEADOS****" +
                "\nCodigo: " + remps.readInt() +
                "\nNombre: " + remps.readUTF() +
                "\nSalario: " + remps.readDouble() +
                "\nSalario: " + remps.readDouble() + " Lps" + 
                "\nFecha de Contratacion: " + remps.readLong();
    }
         
    public boolean EmpleadoActivo(int code) throws IOException{
        remps.seek(0);
        if (code == remps.readInt()){
         return true;   
        }
        return false;
    } 
    
    public boolean despedirEmpleado(int code)throws IOException{
       remps.seek(0);
        if (code == remps.readInt()){
        remps.seek(remps.length()-8);
        remps.writeLong(Calendar.getInstance().getTimeInMillis());
        remps.seek(4);
        System.out.println("Empleado Despedido: "+remps.readUTF());
         return true; 
        }
        return false; 
    }

    
    
}   