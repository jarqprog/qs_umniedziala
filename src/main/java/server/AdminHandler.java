package server;



import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.ControllerAdmin;

import dao.*;
import model.Admin;
import model.Mentor;
import model.User;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import server.sessions.ISessionManager;
import server.webcontrollers.IAdminController;
import server.webcontrollers.WebAdminController;

import java.io.*;
import java.net.URLDecoder;
import java.util.List;
import java.util.stream.Collectors;

public class AdminHandler implements HttpHandler {

    private final IAdminController controller;
    private final ISessionManager sessionManager;

    public static HttpHandler create(ISessionManager sessionManager, IAdminController controller) {
        return new AdminHandler(sessionManager, controller);
    }

    private AdminHandler(ISessionManager sessionManager, IAdminController controller) {
        this.sessionManager = sessionManager;
        this.controller = controller;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        IAdminController webAdminController =   WebAdminController.create( new DaoMentor(), new DaoClass(), new DaoLevel());
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
                }
            }
            if (method.equals("POST")) {
                InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String formData = br.readLine();
                System.out.println(formData);
                String mentorName = parseFromData(formData);
                String uri = httpExchange.getRequestURI().toString();


                System.out.println("URI: " + uri);
                switch (uri) {
                    case "/admin":
                        displayAdminHomePage(httpExchange);
                        break;
                    case "/admin/display_mentor": showMentorDetails(httpExchange, mentorName, webAdminController);
                }
            }
        }
    }


    private void showMentorDetails(HttpExchange httpExchange, String name, IAdminController webAdminController) {
        String mentorInfo = webAdminController.seeMentorData(name);
        String uri = httpExchange.getRequestURI().toString();
        System.out.println("URI: " + uri);
        DaoMentor mentorDao = new DaoMentor();
        List<Mentor> mentors = mentorDao.getAllMentors();
        List<String> mentorsNames = mentors.stream().map(User::getName).collect(Collectors.toList());
        JtwigTemplate template =
                JtwigTemplate.classpathTemplate(
                        "static/admin/display_mentor.html.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("mentors", mentors);
        model.with("MentorsNames", mentorsNames);
        model.with("MentorInfo", mentorInfo);
        String response = template.render(model);
        executeResponse(httpExchange, response);
    }

    private void displayMentor(HttpExchange httpExchange) {
        DaoMentor mentorDao = new DaoMentor();
        List<Mentor> mentors = mentorDao.getAllMentors();
        List<String> mentorsNames = mentors.stream().map(User::getName).collect(Collectors.toList());
        String info = "choose mentor to display";
        JtwigTemplate template =
                JtwigTemplate.classpathTemplate(
                        "static/admin/display_mentor.html.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("mentors", mentors);
        model.with("MentorsNames", mentorsNames);
        model.with("MentorInfo", info);
        String response = template.render(model);
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

    private void executeResponse(HttpExchange httpExchange, String response) {
        byte[] bytes = response.getBytes();
        try {
            httpExchange.sendResponseHeaders(200, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        OutputStream os = httpExchange.getResponseBody();
        try {
            os.write(response.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String parseFromData(String formData) throws UnsupportedEncodingException {
        String[] pairs = formData.split("=");
        String name = pairs[1].replace("+", " ");
        String value = URLDecoder.decode(name, "UTF-8");
        return value;
    }
}