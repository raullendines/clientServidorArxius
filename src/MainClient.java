import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Scanner;

public class MainClient {
    static SocketsControllers sC;
    static Socket socket;
    public static void main(String[] args) throws Exception {
        try {
            socket = new Socket(SocketsControllers.IP, SocketsControllers.PORT);
            sC = new SocketsControllers(socket);
            menu();
        } catch (Exception Ignored) {
            System.out.println("Error al conectarse con el servidor");
        }
    }
    static void menu(){
        Scanner sc = new Scanner(System.in);
        try {
            int opcion;
            do {
                System.out.print("Que quiere hacer: " +
                        "\n1.-Subir archivo " +
                        "\n2.-Descargar archivo " +
                        "\n0.-Salir" +
                        "\nOpción: ");
                opcion = sc.nextInt();

                switchAction(opcion);
            } while (opcion != 0);
        } catch (Exception ignore) {
            System.out.println("Error.");
        }
    }

    static void switchAction(int opcion) throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        String filename;
        switch (opcion) {
            case 1:
                sC.actionClient("Escuchar");
                System.out.println("¿Que fichero quieres enviar?");
                System.out.println("Lista de archivos: ");

                File folder = new File(SocketsControllers.dirBase);
                    File[] filesClient = folder.listFiles();
                    showFiles(filesClient);

                System.out.print("Nombre del archivo: ");
                    filename = sc.next();
                sC.sendFileClient(filename);
                break;
            case 2:
                System.out.println("¿Que fichero quieres bajarte?");
                System.out.println("Lista de archivos: ");

                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    File[] filesServer = (File[]) ois.readObject();
                    showFiles(filesServer);

                    System.out.print("Nombre del archivo: ");
                        filename = sc.next();
                sC.actionClient("Enviar"+":"+filename);
                sC.saveFileClient(filename);
                break;
            case 0:
                sC.actionClient("Cerrar");
                System.out.println("Exit");
                break;
        }
    }

    public static void showFiles( File[] files){
        for (File file : files) {
            if (!file.getName().startsWith(".")){
                System.out.println(file.getName());
            }
        }
    }
}
