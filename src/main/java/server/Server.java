package server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import dao.*;
import server.helpers.ResponseManager;
import server.sessions.ISessionManager;
import server.sessions.SessionManager;
import server.webcontrollers.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server implements IServer {


    private final int PORT;

    private final long MAX_SESSION_DURATION = 300000;  // in milliseconds
    private final ISessionManager sessionManager = SessionManager.create(MAX_SESSION_DURATION);
    private final IDaoFactory daoFactory;

    private Server(int port, IDaoFactory daoFactory){
        PORT = port;
        this.daoFactory = daoFactory;
    }

    public static IServer getInstance(int port, IDaoFactory daoFactory){
        return new Server(port, daoFactory);
    }

    @Override
    public void run() throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        server.createContext("/static", createStaticHandler());
        server.createContext("/", createLoginHandler());
        server.createContext("/admin", createAdminHandler());
        server.createContext("/student", createStudentHandler());
        server.createContext("/mentor", createMentorHandler());
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
        return Login.create(daoFactory.create(DaoLogin.class), sessionManager);
    }

    private HttpHandler createAdminHandler() {
        IAdminController controller = WebAdminController
                .create(daoFactory.create(DaoAdmin.class), daoFactory.create(DaoMentor.class),
                        daoFactory.create(DaoClass.class), daoFactory.create(DaoLevel.class));
        return AdminHandler.create(sessionManager, controller, ResponseManager.create());
    }


    private HttpHandler createMentorHandler() {
        IMentorController controller = WebMentorController
                .create(daoFactory.create(DaoWallet.class), daoFactory.create(DaoStudent.class),
                        daoFactory.create(DaoArtifact.class), daoFactory.create(DaoLevel.class),
                        daoFactory.create(DaoTeam.class), daoFactory.create(DaoClass.class),
                        daoFactory.create(DaoQuest.class), daoFactory.create(DaoMentor.class));
        return MentorHandler.create(sessionManager, controller, ResponseManager.create());
    }


    private HttpHandler createStudentHandler() {
        IStudentController controller = WebStudentController
                .create(daoFactory.create(DaoWallet.class), daoFactory.create(DaoStudent.class),
                        daoFactory.create(DaoArtifact.class), daoFactory.create(DaoLevel.class),
                        daoFactory.create(DaoTeam.class), daoFactory.create(DaoClass.class));
        return StudentHandler.create(sessionManager, controller, ResponseManager.create());
    }

    private HttpHandler createLogoutHandler() {
        return Logout.create(sessionManager);
    }
}