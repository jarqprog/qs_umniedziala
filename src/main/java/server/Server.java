package server;

import com.sun.net.httpserver.HttpServer;
import dao.DaoLogin;
import dao.IDaoLogin;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server implements IServer {


    private final int PORT;

    private Server(int port){

        PORT = port;
    }

    public static IServer getInstance(int port){

        return new Server(port);
    }

    @Override
    public void run() throws IOException {
        IDaoLogin loginDao = new DaoLogin();
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/static", new Static());
        server.createContext("/login", new Login(loginDao));
        // set routes
        server.setExecutor(null); // creates a default executor
        // start listening
        server.start();

    }



}
