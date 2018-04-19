package server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import server.helpers.IResponseManager;
import server.sessions.ISessionManager;
import server.webcontrollers.IAdminController;

import java.io.*;
import java.net.URLDecoder;
import java.util.List;

public class AdminHandler implements HttpHandler {

    private final IAdminController controller;
    private final ISessionManager sessionManager;
    private final IResponseManager responseManager;

    public static HttpHandler create(ISessionManager sessionManager,
                                     IAdminController controller,
                                     IResponseManager responseManager) {
        return new AdminHandler(sessionManager, controller, responseManager);
    }

    private AdminHandler(ISessionManager sessionManager,
                         IAdminController controller,
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
                    case "/admin":
                        displayAdminHomePage(httpExchange);
                        break;
                    case "/admin/create_mentor": createMentor(httpExchange);
                        break;
                    case "/admin/display_mentor":
                        displayMentor(httpExchange);
                        break;
                    case "/admin/create_class":
                        displayCreateClassPage(httpExchange);
                        break;
                }
            }
            if (method.equals("POST")) {
                InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String formData = br.readLine();
                System.out.println(formData);

                String uri = httpExchange.getRequestURI().toString();


                System.out.println("URI: " + uri);
                switch (uri) {
                    case "/admin":
                        displayAdminHomePage(httpExchange);
                        break;
                    case "/admin/display_mentor":
                        String mentorName = parseFromDataEditMentor(formData);
                        showMentorDetails(httpExchange, mentorName);
                        break;
                    case "/admin/create_class":
                        String className = parseFormDataCreateClass(formData);
                        saveClassToDb(httpExchange, className);
                        break;
                }
            }
        }
    }

    private String parseFormDataCreateClass(String formData) throws UnsupportedEncodingException {
        String[] input = formData.split("=");
        String className = input[1];
        return URLDecoder.decode(className, "UTF-8");
    }


    private void saveClassToDb(HttpExchange httpExchange, String className) throws IOException {
        String info;
        if(controller.createClass(className)){
            info = "Class added successfully!";
        }else{
            info = "Something went wrong :(";
        }
        String response;
        JtwigTemplate template = JtwigTemplate.classpathTemplate(
                                "static/admin/create_class.html");
        JtwigModel model = JtwigModel.newModel();
        model.with("info", info);
        response =template.render(model);
        responseManager.executeResponse(httpExchange, response);
    }

    private void displayCreateClassPage(HttpExchange httpExchange) throws IOException {
        String response;
        JtwigTemplate template = JtwigTemplate.classpathTemplate(
                                "static/admin/create_class.html");
        JtwigModel model = JtwigModel.newModel();
        response = template.render(model);
        responseManager.executeResponse(httpExchange,response);


    }


    private void redirectToLogin(HttpExchange httpExchange) throws IOException {
        Headers responseHeaders = httpExchange.getResponseHeaders();
        responseHeaders.add("Location", "/");
        httpExchange.sendResponseHeaders(302, -1);
        httpExchange.close();
    }

    // te dwie metody niżej są bardzo podobne... - zrobiłem refactor przy użyciu controllera - żeby nie wynosić modeli do handlera

    private void showMentorDetails(HttpExchange httpExchange, String name) throws IOException {
        String mentorInfo = controller.seeMentorData(name);
        String uri = httpExchange.getRequestURI().toString();
        System.out.println("URI: " + uri);
        List<String> mentorsFullData = controller.getMentorsFullData();
        List<String> mentorsNames = controller.getMentorsNames();
        JtwigTemplate template =
                JtwigTemplate.classpathTemplate(
                        "static/admin/display_mentor.html.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("mentors", mentorsFullData);
        model.with("MentorsNames", mentorsNames);
        model.with("MentorInfo", mentorInfo);
        String response = template.render(model);
        responseManager.executeResponse(httpExchange, response);
    }

    private void displayMentor(HttpExchange httpExchange) throws IOException {
        List<String> mentorsFullData = controller.getMentorsFullData();
        List<String> mentorsNames = controller.getMentorsNames();
        String info = "choose mentor to display";
        JtwigTemplate template =
                JtwigTemplate.classpathTemplate(
                        "static/admin/display_mentor.html.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("mentors", mentorsFullData);
        model.with("MentorsNames", mentorsNames);
        model.with("MentorInfo", info);
        String response = template.render(model);
        responseManager.executeResponse(httpExchange, response);
    }

 

    private void createMentor(HttpExchange httpExchange) throws IOException {
        String response;
        System.out.println("jestem");
        JtwigTemplate template =
                JtwigTemplate.classpathTemplate(
                        "static/admin/create_mentor.html");

        JtwigModel model = JtwigModel.newModel();
        response = template.render(model);
        responseManager.executeResponse(httpExchange, response);
    }

    private void displayAdminHomePage(HttpExchange httpExchange) throws IOException {
        String response;

        JtwigTemplate template =
                JtwigTemplate.classpathTemplate(
                        "static/admin/profile.html.twig");
        JtwigModel model = JtwigModel.newModel();
        int adminId = sessionManager.getCurrentUserId(httpExchange);
        model.with("name", controller.getAdminName(adminId));
        model.with("email", controller.getAdminEmail(adminId));
        response = template.render(model);
        responseManager.executeResponse(httpExchange, response);
    }
// parseFromDataEditMentor is unique for every form
    private String parseFromDataEditMentor(String formData) throws UnsupportedEncodingException {
        String[] pairs = formData.split("=");
        String name = pairs[1].replace("+", " ");
        return URLDecoder.decode(name, "UTF-8");
    }
}