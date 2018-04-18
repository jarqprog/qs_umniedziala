package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.ControllerAdmin;
import dao.DaoAdmin;
import dao.DaoMentor;
import model.Admin;
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
        String method = httpExchange.getRequestMethod();
        System.out.println(method);
        String response;
        int loggedUserId = sessionManager.getCurrentUserId(httpExchange);
//        System.out.println("logged user: " + loggedUserId);
        if( loggedUserId == -1) {
            response = "powinien wylogowac admina!!";

            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();

        } else {
            if (method.equals("GET")) {
                String uri = httpExchange.getRequestURI().toString();
                System.out.println(uri);
                switch(uri){
                    case "/admin": displayAdminHomePage(httpExchange);
                        break;
                    case "/admin/create_mentor": createMentor(httpExchange);
                        break;
                    case "/admin/display_mentor": displayMentor(httpExchange);
                        break;
                }
            }
        }
    }

    private void displayMentor(HttpExchange httpExchange) throws IOException {
        String response;
        DaoMentor dao = new DaoMentor();

        JtwigTemplate template =
                JtwigTemplate.classpathTemplate(
                        "static/admin/display_mentor.html");

        JtwigModel model = JtwigModel.newModel();
        model.with("mentors", dao.getAllMentors());
        response = template.render(model);
        executeResponse(httpExchange, response);

    }

    private void createMentor(HttpExchange httpExchange) throws IOException {
        String response;
        System.out.println("jestem");
        JtwigTemplate template =
                JtwigTemplate.classpathTemplate(
                        "static/admin/create_mentor.html");

        JtwigModel model = JtwigModel.newModel();
        response = template.render(model);
        executeResponse(httpExchange, response);
    }

    private void displayAdminHomePage(HttpExchange httpExchange) throws IOException {
        String response;
        DaoAdmin daoAdmin = new DaoAdmin();
        int userId = this.sessionManager.getCurrentUserId(httpExchange);
        Admin admin = daoAdmin.importAdmin(userId);

        JtwigTemplate template =
                JtwigTemplate.classpathTemplate(
                        "static/admin/profile.html.twig");

        JtwigModel model = JtwigModel.newModel();
        model.with("name", admin.getName());
        model.with("email", admin.getEmail());
        response = template.render(model);
        executeResponse(httpExchange, response);
    }

    private void executeResponse(HttpExchange httpExchange, String response) throws IOException {
        byte[] bytes = response.getBytes();
        httpExchange.sendResponseHeaders(200, bytes.length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}