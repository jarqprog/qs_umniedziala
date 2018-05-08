package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import server.helpers.IResponseManager;
import server.sessions.ISessionManager;
import server.webcontrollers.IMentorController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MentorHandler implements HttpHandler {

    private final IMentorController controller;
    private final ISessionManager sessionManager;
    private final IResponseManager responseManager;


    public static HttpHandler create(ISessionManager sessionManager,
                                     IMentorController controller,
                                     IResponseManager responseManager) {
        return new MentorHandler(sessionManager, controller, responseManager);
    }

    private MentorHandler(ISessionManager sessionManager,
                          IMentorController controller,
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
                    case "/mentor":
                        displayMentorHomePage(httpExchange);
                        break;
                    case "/mentor/add_quest":
                        displayAddQuestPage(httpExchange);
                        break;
                    case "/mentor/add_artifact":
                        displayAddArtifactPage(httpExchange);
                        break;
                    case "/mentor/see_all_wallets":
                        displayAllWallets(httpExchange);
                        break;
                }
            }
            if (method.equals("POST")) {
                String uri = httpExchange.getRequestURI().toString();
                switch (uri) {
                    case "/mentor/add_quest":
                        addQuest(httpExchange);
                        break;
                    case "/mentor/add_artifact":
                        addArtifact(httpExchange);
                        break;
                }
            }
        }
    }

    private void displayMentorHomePage(HttpExchange httpExchange) throws IOException {
        String response;
        JtwigTemplate template =
                JtwigTemplate.classpathTemplate(
                        "static/mentor/profile.html.twig");
        JtwigModel model = JtwigModel.newModel();
        int mentorId = this.sessionManager.getCurrentUserId(httpExchange);
        model.with("name", controller.getMentorName(mentorId));
        model.with("email", controller.getMentorEmail(mentorId));
        model.with("class", controller.getMentorClassWithStudents(mentorId));
        response = template.render(model);
        responseManager.executeResponse(httpExchange, response);
    }

    private void displayAddQuestPage(HttpExchange httpExchange) throws IOException {
        String response;
        JtwigTemplate template =
                JtwigTemplate.classpathTemplate(
                        "static/mentor/add_quest.html.twig");
        JtwigModel model = JtwigModel.newModel();
        response = template.render(model);
        responseManager.executeResponse(httpExchange, response);
    }

    private void displayAddArtifactPage(HttpExchange httpExchange) throws IOException {
        String response;
        JtwigTemplate template =
                JtwigTemplate.classpathTemplate(
                        "static/mentor/add_artifact.html.twig");
        JtwigModel model = JtwigModel.newModel();
        response = template.render(model);
        responseManager.executeResponse(httpExchange, response);
    }

    private void addQuest(HttpExchange httpExchange) throws IOException{
        Map<String, String> inputs = responseManager.getInput(httpExchange);

        String name = inputs.get("questname");
        int value = Integer.parseInt(inputs.get("value"));
        String description = inputs.get("description");
        String type = inputs.get("type");
        String category = inputs.get("category");

        String info = "Error!";
        if(controller.addQuest(name, value, description, type, category)){
            info = "Done!";
        }
        String response;
        JtwigTemplate template = JtwigTemplate.classpathTemplate(
                "static/mentor/add_quest.html.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("info", info);
        response = template.render(model);
        responseManager.executeResponse(httpExchange, response);
    }

    private void addArtifact(HttpExchange httpExchange) throws IOException {
        Map<String, String> inputs = responseManager.getInput(httpExchange);

        String name = inputs.get("artifactname");
        int value = Integer.parseInt(inputs.get("value"));
        String description = inputs.get("description");
        String type = inputs.get("type");

        String info = "Error!";
        if(controller.addArtifact(name, value, description, type)){
            info = "Done!";
        }
        String response;
        JtwigTemplate template = JtwigTemplate.classpathTemplate(
                "static/mentor/add_artifact.html.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("info", info);
        response = template.render(model);
        responseManager.executeResponse(httpExchange, response);
    }

    private void displayAllWallets(HttpExchange httpExchange) throws IOException{
        Map<String, String> wallets = controller.getAllWallets();
        String response;
        JtwigTemplate template = JtwigTemplate.classpathTemplate(
                "static/mentor/see_all_wallets.html.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("wallets", wallets);
        response = template.render(model);
        responseManager.executeResponse(httpExchange, response);
    }
}