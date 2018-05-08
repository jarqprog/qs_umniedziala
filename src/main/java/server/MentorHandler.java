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
                    case "/mentor/create_student":
                        displayCreateStudentPage(httpExchange);
                        break;
                    case "/mentor/create_team":
                        displayCreateTeamPage(httpExchange);
                        break;
                    case "/mentor/edit_quest":
                        displayQuestToUpdate(httpExchange);
                        break;
                }
            }
            if (method.equals("POST")) {
                String uri = httpExchange.getRequestURI().toString();
                System.out.println("URI: " + uri);

                switch(uri) {
                    case "/mentor/create_student":
                        createStudent(httpExchange);
                        break;
                    case "/mentor/create_team":
                        createTeam(httpExchange);
                        break;
                    case "/mentor/edit_quest":
                        updateQuest(httpExchange);
                        break;
                }
            }
        }
    }

    private void updateQuest(HttpExchange httpExchange) throws IOException {
        List<String> quests = controller.getQuests();
        Map<String, String> inputs = responseManager.parseEditData(httpExchange);
        String info;
        boolean isQuestEdited = controller.editQuest(inputs);
        if(isQuestEdited){
            info = "Quest edited successfully!";
        }else{
            info = "Something went wrong :(";
        }

        JtwigTemplate template =
                JtwigTemplate.classpathTemplate(
                        "static/mentor/edit_quest.html");
        JtwigModel model = JtwigModel.newModel();
        model.with("quests", quests);
        model.with("info", info);
        String response = template.render(model);
        responseManager.executeResponse(httpExchange, response);

    }

    private void displayQuestToUpdate(HttpExchange httpExchange) throws IOException {
        List<String> quests = controller.getQuests();
        String response;
        JtwigTemplate template =
                JtwigTemplate.classpathTemplate(
                        "static/mentor/edit_quest.html");
        JtwigModel model = JtwigModel.newModel();
        model.with("quests", quests);
        response = template.render(model);
        responseManager.executeResponse(httpExchange, response);

    }

    private void createTeam(HttpExchange httpExchange) throws IOException {
        Map<String, String> inputs = responseManager.getInput(httpExchange);
        String teamName = inputs.get("teamname");
        String info;
        if(controller.createTeam(teamName)){
            info = "Student added successfully!";
        }else{
            info = "Something went wrong :(";
        }
        String response;
        JtwigTemplate template =
                JtwigTemplate.classpathTemplate(
                        "static/mentor/create_team.html");
        JtwigModel model = JtwigModel.newModel();
        model.with("info", info);
        response = template.render(model);
        responseManager.executeResponse(httpExchange, response);

    }

    private void createStudent(HttpExchange httpExchange) throws IOException {
        Map<String, String> inputs = responseManager.getInput(httpExchange);
        String classNames = controller.getClassNames();
        String name = inputs.get("firstname");
        String password = inputs.get("password");
        String email = inputs.get("email");
        String id = inputs.get("classId");
        int classId = Integer.parseInt(id);
        String info;
        if(controller.createStudent(name, password, email, classId)){
            info = "Student added successfully!";
        }else{
            info = "Something went wrong :(";
        }
        String response;
        JtwigTemplate template =
                JtwigTemplate.classpathTemplate(
                        "static/mentor/create_student.html");
        JtwigModel model = JtwigModel.newModel();
        model.with("classNames", classNames);
        model.with("info", info);
        response = template.render(model);
        responseManager.executeResponse(httpExchange, response);
    }

    private void displayCreateStudentPage(HttpExchange httpExchange) throws IOException {
        String response;
        String classNames = controller.getClassNames();
        JtwigTemplate template =
                JtwigTemplate.classpathTemplate(
                        "static/mentor/create_student.html");
        JtwigModel model = JtwigModel.newModel();
        model.with("classNames", classNames);
        response = template.render(model);
        responseManager.executeResponse(httpExchange, response);
    }

    private void displayCreateTeamPage(HttpExchange httpExchange) throws IOException {
        String response;
        JtwigTemplate template =
                JtwigTemplate.classpathTemplate(
                        "static/mentor/create_team.html");
        JtwigModel model = JtwigModel.newModel();
        response = template.render(model);
        responseManager.executeResponse(httpExchange, response);
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