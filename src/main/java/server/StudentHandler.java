package server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import server.helpers.IResponseManager;
import server.sessions.ISessionManager;
import server.webcontrollers.IStudentController;

import java.io.IOException;

public class StudentHandler implements HttpHandler {

    private final IStudentController controller;
    private final ISessionManager sessionManager;
    private final IResponseManager responseManager;


    public static HttpHandler create(ISessionManager sessionManager,
                                     IStudentController controller,
                                     IResponseManager responseManager) {
        return new StudentHandler(sessionManager, controller, responseManager);
    }

    private StudentHandler(ISessionManager sessionManager,
                           IStudentController controller,
                           IResponseManager responseManager) {
        this.sessionManager = sessionManager;
        this.controller = controller;
        this.responseManager = responseManager;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        System.out.println(method);

        int loggedUserId = sessionManager.getCurrentUserId(httpExchange);

        if( loggedUserId == -1) {
            redirectToLogin(httpExchange);

        } else {
            if (method.equals("GET")) {
                System.out.println(method);
                String uri = httpExchange.getRequestURI().toString();
                System.out.println(uri);
                switch (uri) {
                    case "/student":
                        displayStudentHomePage(httpExchange);
                        break;
                }
            }
            if (method.equals("POST")) {

                // to implement
            }
        }
    }

    private void redirectToLogin(HttpExchange httpExchange) throws IOException {
        Headers responseHeaders = httpExchange.getResponseHeaders();
        responseHeaders.add("Location", "/");
        httpExchange.sendResponseHeaders(302, -1);
        httpExchange.close();
    }

    private void displayStudentHomePage(HttpExchange httpExchange) throws IOException {
        String response;
        JtwigTemplate template =
                JtwigTemplate.classpathTemplate(
                        "static/student/profile.html.twig");

        JtwigModel model = JtwigModel.newModel();
        int studentId = this.sessionManager.getCurrentUserId(httpExchange);
        model.with("name", controller.getStudentName(studentId));
        model.with("email", controller.getStudentEmail(studentId));
        model.with("money", controller.getStudentWallet(studentId));
        model.with("level", controller.getStudentExpLevel(studentId));
        model.with("group", controller.getStudentGroup(studentId));
        model.with("class", controller.getStudentGroup(studentId));
        response = template.render(model);
        responseManager.executeResponse(httpExchange, response);
    }
}