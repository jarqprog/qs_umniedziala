package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.ControllerAdmin;

import dao.DaoAdmin;
import dao.IDaoLogin;
import model.Admin;
import model.Mentor;
import model.Student;
import model.User;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import server.sessions.ISessionManager;

import java.io.*;

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

            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();



        } else {
            DaoAdmin daoAdmin = new DaoAdmin();
            int userId = this.sessionManager.getCurrentUserId(httpExchange);
            Admin admin = daoAdmin.importAdmin(userId);

            JtwigTemplate template =
                                JtwigTemplate.classpathTemplate(
                                        "static/user-admin/profile.html.twig");

            JtwigModel model = JtwigModel.newModel();
            response = template.render(model);
            byte [] bytes = response.getBytes();
            httpExchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();

            model.with("name", admin.getName());
            model.with("email", admin.getEmail());
            response = template.render(model);
            executeResponse(httpExchange, response);
            return;

        }


    }

    private void executeResponse(HttpExchange httpExchange, String response) throws IOException {
        byte[] bytes = response.getBytes();
        httpExchange.sendResponseHeaders(200, bytes.length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    
}
