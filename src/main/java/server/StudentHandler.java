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
import java.util.List;

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
            responseManager.redirectToLogin(httpExchange);

        } else {
            if (method.equals("GET")) {
                System.out.println(method);
                String uri = httpExchange.getRequestURI().toString();
                System.out.println(uri);
                switch (uri) {
                    case "/student":
                        displayStudentHomePage(httpExchange);
                        break;
                    case "/student/menage_team":
                        displayStudentTeamData(httpExchange);
                        break;
                    case "/student/buy_artifact":
                        displayArtifacts(httpExchange);
                        break;
                }
            }
            if (method.equals("POST")) {

                // to implement
            }
        }
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
        model.with("class", controller.getStudentClass(studentId));
        response = template.render(model);
        responseManager.executeResponse(httpExchange, response);
    }

    private void displayStudentTeamData(HttpExchange httpExchange) throws IOException{
        int studentId = this.sessionManager.getCurrentUserId(httpExchange);
        String teamName = controller.getStudentGroup(studentId);
        List<String> memebrs = controller.getTeamMembers(studentId);
        String response;
        JtwigTemplate template =
                JtwigTemplate.classpathTemplate(
                        "static/student/menage_team.html.twig");

        JtwigModel model = JtwigModel.newModel();
        model.with("teamName", teamName);
        model.with("members", memebrs);
        response = template.render(model);
        responseManager.executeResponse(httpExchange, response);
    }

    private void displayArtifacts(HttpExchange httpExchange) throws IOException{
        List<String> artifacts = controller.getArtifacts();
        String response;
        JtwigTemplate template =
                JtwigTemplate.classpathTemplate(
                        "static/student/buy_artifact.html.twig");

        JtwigModel model = JtwigModel.newModel();
        model.with("artifacts", artifacts);
        response = template.render(model);
        responseManager.executeResponse(httpExchange, response);
    }

}