import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class SocketsControllers{

    private static Socket socket;
    public static final int PORT = 5432;
    public static final String IP = "localhost";
    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;
    static final String dirBase = "src/archivos/";
    static InputStream inputStream;
    public SocketsControllers(Socket socket) throws IOException {
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        inputStream = socket.getInputStream();
        dataInputStream = new DataInputStream(inputStream);
        dataInputStream = new DataInputStream(inputStream);
    }


    public static void actionClient(String messageAction) {
        try {
            String[] msg = messageAction.split(":");
            if (msg[0].equals("Escuchar")) {
                dataOutputStream.writeUTF(messageAction);
            } else if (msg[0].equals("Enviar")) {
                dataOutputStream.writeUTF(messageAction);
            } else if (msg[0].equals("Cerrar")) {
                dataOutputStream.writeUTF(messageAction);
                socket.close();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void sendFileClient(String fileName) {
        try {
            FileInputStream fileInputStream = new FileInputStream(dirBase + fileName);
            dataOutputStream.writeUTF(fileName);
            byte[] buffer = new byte[4096];
            int read;
            while ((read = fileInputStream.read(buffer, 0, buffer.length)) > 0) {
                System.out.println("Enviando datos... ");
                dataOutputStream.write(buffer, 0, read);
            }
            System.out.println("Transferencia completada.");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    static void saveFileClient(String fileName){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(dirBase + fileName);
            byte[] buffer = new byte[4096];
            int read = dataInputStream.read(buffer);
            fileOutputStream.write(buffer, 0, read);
            System.out.println("Transferencia completada.");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
