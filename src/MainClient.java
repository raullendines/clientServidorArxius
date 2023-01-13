import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class MainClient {
    static SocketsControllers sC;
    public static void main(String[] args) throws Exception {
        try {
            Socket socket = new Socket(SocketsControllers.IP, SocketsControllers.PORT);
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

    static void switchAction(int opcion) {
        Scanner sc = new Scanner(System.in);
        String filename;
        switch (opcion) {
            case 1:
                sC.actionClient("Escuchar");
                System.out.println("¿Que fichero quieres enviar?");
                filename = sc.next();
                sC.sendFileClient(filename);
                break;
            case 2:
                System.out.println("Que fichero quieres bajarte");
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
}
