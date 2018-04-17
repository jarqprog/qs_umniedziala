package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.IDaoLogin;
import model.Admin;
import model.Mentor;
import model.Student;
import model.User;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class Login implements HttpHandler {

    private IDaoLogin loginDao;

    public Login(IDaoLogin loginDao) {
        this.loginDao = loginDao;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            dislayLoginPage(httpExchange);
        }

        if (method.equals("POST")) {
            Map inputs = getInput(httpExchange);
            User user = getUser(inputs.get("email").toString(), inputs.get("password").toString());

            if(user!=null) {
                logUser(httpExchange, user);
            } else {
                displayLoginFailure(httpExchange);
            }
        }
    }

    private void displayLoginFailure(HttpExchange httpExchange) throws IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("static/index.html.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("user", "invalid");
        String response = template.render(model);

        executeResponse(httpExchange, response, response.length());
    }

    private void logUser(HttpExchange httpExchange, User user) {
        String status = getUserRole(user);
        JtwigTemplate template =
                                JtwigTemplate.classpathTemplate(
                                "static/user-" + status + "/" + status + "_profile.html.twig");

        JtwigModel model = JtwigModel.newModel();
        setModel(model, status, user);
        String response = template.render(model);
        try {
            executeResponse(httpExchange, response, response.length() + 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void executeResponse(HttpExchange httpExchange, String response, int i) throws IOException {
        httpExchange.sendResponseHeaders(200, i);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }



    private Map getInput(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();
        System.out.println(formData);
        return parseFormData(formData);
    }

    private void dislayLoginPage(HttpExchange httpExchange) throws IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("static/index.html.twig");
        JtwigModel model = JtwigModel.newModel();
        String response = template.render(model);

        executeResponse(httpExchange, response, response.length());
    }

    private void setModel(JtwigModel model, String status, User user) {
        if (status.equals("admin")) {
            AdminPage.setModel(user, model);
        } else if (status.equals("mentor")) {
            MentorPage.setModel(((Mentor) user), model);
        } else if (status.equals("student")) {
            StudentPage.setModel(((Student) user), model);
        }
    }

    private String getUserRole(User user) {
        String status = null;
        if (user instanceof Admin) {
            status = "admin";
        } else if (user instanceof Mentor) {
            status = "mentor";
        } else if (user instanceof Student) {
            status = "student";
        }
        return status;
    }

    private User getUser(String email, String password) {
        return loginDao.getUser(email, password);
    }

    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }
}

