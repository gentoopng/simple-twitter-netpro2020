import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleTwitterMain {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(5001);
            System.out.println("Server is waiting at port 5001");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}