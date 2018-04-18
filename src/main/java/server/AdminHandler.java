package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.ControllerAdmin;
import dao.IDaoLogin;
import model.Admin;
import model.Mentor;
import model.Student;
import model.User;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import server.sessions.ISessionManager;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class AdminHandler implements HttpHandler {

    private final ControllerAdmin controller;
    private final ISessionManager sessionManager;

    public static HttpHandler create(ISessionManager sessionManager, ControllerAdmin controller) {
        return new AdminHandler(sessionManager, controller);
    }

    private AdminHandler(ISessionManager sessionManager, ControllerAdmin controller) {
        this.sessionManager = sessionManager;
        this.controller = controller;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String response;
        if( sessionManager.getCurrentUserId(httpExchange) == -1) {
            response = "powinien wylogowac admina!!";
        } else {
            response = "admin powinien byc zalogowany";
        }

        httpExchange.sendResponseHeaders(400, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
