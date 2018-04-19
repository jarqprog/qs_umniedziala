package server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import controller.ControllerAdmin;
import dao.*;
import server.helpers.ResponseManager;
import server.sessions.ISessionManager;
import server.sessions.SessionManager;
import server.webcontrollers.IAdminController;
import server.webcontrollers.IStudentController;
import server.webcontrollers.WebAdminController;
import server.webcontrollers.WebStudentController;
import view.ViewAdmin;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server implements IServer {


    private final int PORT;

    private final long MAX_SESSION_DURATION = 300000;  // in milliseconds
    private final ISessionManager sessionManager = SessionManager.create(MAX_SESSION_DURATION);

    private Server(int port){
        PORT = port;
    }

    public static IServer getInstance(int port){
        return new Server(port);
    }

    @Override
    public void run() throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        server.createContext("/static", createStaticHandler());
        server.createContext("/", createLoginHandler());
        server.createContext("/admin", createAdminHandler());
        server.createContext("/student", createStudentHandler());
        server.createContext("/logout", createLogoutHandler());

        // set routes
        server.setExecutor(null); // creates a default executor
        // start listening
        server.start();
    }


    // initialize objects

    private HttpHandler createStaticHandler() {
        return Static.create();
    }

    private HttpHandler createLoginHandler() {
        return Login.create(new DaoLogin(), sessionManager);
    }

    private HttpHandler createAdminHandler() {
        IAdminController controller = WebAdminController
                .create(new DaoAdmin(), new DaoMentor(), new DaoClass(), new DaoLevel());
        return AdminHandler.create(sessionManager, controller, ResponseManager.create());
    }

    private HttpHandler createStudentHandler() {
        IStudentController controller = WebStudentController
                .create(new DaoWallet(), new DaoStudent(),
                        new DaoArtifact(), new DaoLevel(), new DaoTeam(), new DaoClass());
        return StudentHandler.create(sessionManager, controller, ResponseManager.create());
    }

    private HttpHandler createLogoutHandler() {
        return Logout.create(sessionManager);
    }
}