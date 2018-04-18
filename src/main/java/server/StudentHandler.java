package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.sessions.ISessionManager;
import server.webcontrollers.IStudentController;
import java.io.IOException;

public class StudentHandler implements HttpHandler {

private final IStudentController controller;
private final ISessionManager sessionManager;

    public static HttpHandler create(ISessionManager sessionManager, IStudentController controller) {
        return new StudentHandler(sessionManager, controller);
    }

    private StudentHandler(ISessionManager sessionManager, IStudentController controller) {
        this.sessionManager = sessionManager;
        this.controller = controller;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        int loggedUserId = sessionManager.getCurrentUserId(httpExchange);
        if(loggedUserId == -1) {
            // trzeba przekierować na main page lub podać info, że sesja wygasła, przejdź do logowania
            System.out.println("powinien być wylogowany");
        }

        

    }

}