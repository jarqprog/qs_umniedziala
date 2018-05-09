package server;

import com.sun.net.httpserver.HttpServer;

import server.factory.IHandlerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server implements IServer {

    private final int PORT;

    private final IHandlerFactory handlerFactory;

    private Server(int port, IHandlerFactory handlerFactory){
        PORT = port;
        this.handlerFactory = handlerFactory;
    }

    public static IServer getInstance(int port, IHandlerFactory handlerFactory){
        return new Server(port, handlerFactory);
    }

    @Override
    public void run() throws IOException {
        System.out.println("http://localhost:8080/");
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        server.createContext("/static", handlerFactory.create(Static.class));
        server.createContext("/", handlerFactory.create(Login.class));
        server.createContext("/admin", handlerFactory.create(AdminHandler.class));
        server.createContext("/student", handlerFactory.create(StudentHandler.class));
        server.createContext("/mentor", handlerFactory.create(MentorHandler.class));
        server.createContext("/logout", handlerFactory.create(Logout.class));

        // set routes
        server.setExecutor(null); // creates a default executor
        // start listening
        server.start();
    }
}