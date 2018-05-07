package server;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import server.helpers.IResponseManager;
import server.sessions.ISessionManager;
import server.webcontrollers.IMentorController;

import java.io.IOException;

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
                }
            }
            if (method.equals("POST")) {

                // to implement
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
}