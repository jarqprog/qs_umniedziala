import server.Server;

import java.io.IOException;

public class App {
    public static void main(String [] args) throws IOException {
        Server.getInstance(8080).run();
        System.out.println("http://localhost:8080/login");
    }
}