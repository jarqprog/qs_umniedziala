package server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import dao.DaoLogin;
import dao.IDaoLogin;
import server.sessions.ISessionManager;
import server.sessions.SessionManager;

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

        // initialize objects
        IDaoLogin loginDao = new DaoLogin();

        final long MAX_SESSION_DURATION = 300000;  // in milliseconds
        ISessionManager sessionManager = SessionManager.create(MAX_SESSION_DURATION);

        HttpHandler staticHandler = Static.create();
        HttpHandler loginHandler = Login.create(loginDao, sessionManager);

        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/static", staticHandler);
        server.createContext("/login", loginHandler);

        // set routes
        server.setExecutor(null); // creates a default executor
        // start listening
        server.start();
    }
}
