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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                    case "/admin/create_level":
                        showExperienceLevelCreation(httpExchange);
                        break;
                }
            }
            if (method.equals("POST")) {
                String uri = httpExchange.getRequestURI().toString();


                System.out.println("URI: " + uri);
                switch (uri) {
                    case "/admin":
                        displayAdminHomePage(httpExchange);
                        break;
                    case "/admin/display_mentor":
                        showMentorDetails(httpExchange);
                        break;
                    case "/admin/create_level":
                        handleExperienceLevelCreation(httpExchange);
                        break;

                }
            }
        }
    }

    private void redirectToLogin(HttpExchange httpExchange) throws IOException {
        Headers responseHeaders = httpExchange.getResponseHeaders();
        responseHeaders.add("Location", "/");
        httpExchange.sendResponseHeaders(302, -1);
        httpExchange.close();
    }

    // te dwie metody niżej są bardzo podobne... - zrobiłem refactor przy użyciu controllera - żeby nie wynosić modeli do handlera

    private void showMentorDetails(HttpExchange httpExchange) throws IOException {
        String mentorName = parseFromData(httpExchange);
        String mentorInfo = controller.seeMentorData(mentorName);
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

    private String parseFromData(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();
        String[] pairs = formData.split("=");
        String name = pairs[1].replace("+", " ");
        return URLDecoder.decode(name, "UTF-8");
    }

    private void showExperienceLevelCreation(HttpExchange httpExchange) throws IOException {
        String response;
        JtwigTemplate template =
                JtwigTemplate.classpathTemplate(
                        "static/admin/create_level.html");
        JtwigModel model = JtwigModel.newModel();
        model.with("result", "");
        response = template.render(model);
        responseManager.executeResponse(httpExchange, response);
    }

    private void handleExperienceLevelCreation(HttpExchange httpExchange) throws IOException {
        Map<String,String> inputs = parseFormDataToMap(httpExchange);
        String levelName = inputs.get("level_name");
        int coinsLimit = Integer.parseInt(inputs.get("coins_limit"));
        System.out.println("Inputs: " + inputs.toString());
        boolean isExportSuccess =  controller.createLevel(levelName, coinsLimit);
        String result;
        if(! isExportSuccess) {
            result = "creation failure, try again";
        } else {
            result = "creation success!";
        }
        System.out.println(result);

        String response;
        JtwigTemplate template =
                JtwigTemplate.classpathTemplate(
                        "static/admin/create_level.html");
        JtwigModel model = JtwigModel.newModel();
        model.with("result", result);
        response = template.render(model);

        responseManager.executeResponse(httpExchange, response);
    }

    private Map<String,String> parseFormDataToMap(HttpExchange httpExchange) throws IOException, UnsupportedEncodingException {

        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String data = br.readLine();
        Map<String,String> map = new HashMap<>();
        System.out.println("parser: " + data);
        String[] pairs = data.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            String value = URLDecoder.decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }
}