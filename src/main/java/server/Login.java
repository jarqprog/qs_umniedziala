package server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import system.dao.IDaoLogin;
import system.model.Admin;
import system.model.Mentor;
import system.model.Student;
import system.model.User;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import server.sessions.ISessionManager;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class Login implements HttpHandler {

    private final IDaoLogin loginDao;
    private final ISessionManager sessionManager;

    public static HttpHandler create(IDaoLogin loginDao,
                                     ISessionManager sessionManager) {
        return new Login(loginDao, sessionManager);
    }

    private Login(IDaoLogin loginDao, ISessionManager sessionManager) {
        this.loginDao = loginDao;
        this.sessionManager = sessionManager;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            displayLoginPage(httpExchange);
        }

        if (method.equals("POST")) {
            Map<String,String> inputs = getInput(httpExchange);
            User user = getUser(inputs.get("email"), inputs.get("password"));

            if(user!=null) {
                logUser(httpExchange, user);
            } else {
                displayLoginFailure(httpExchange);
            }
        }
    }

    private void displayLoginFailure(HttpExchange httpExchange) throws IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("static/index.html");
        JtwigModel model = JtwigModel.newModel();
        model.with("user", "invalid");
        String response = template.render(model);

        executeResponse(httpExchange, response, response.length());
    }

    private void logUser(HttpExchange httpExchange, User user) throws IOException {
        String status = getUserRole(user);

        sessionManager.register(httpExchange, user.getUserId(), status);

        Headers responseHeaders = httpExchange.getResponseHeaders();
        switch (status) {
            case "admin":
                responseHeaders.add("Location", "/admin");
                break;
            case "mentor":
                responseHeaders.add("Location", "/mentor");
                break;
            case "student":
                responseHeaders.add("Location", "/student");
                break;
            default:
                responseHeaders.add("Location", "/");
        }
        httpExchange.sendResponseHeaders(302, -1);
        httpExchange.close();
    }

    private void executeResponse(HttpExchange httpExchange, String response, int i) throws IOException {

        httpExchange.sendResponseHeaders(200, i);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private Map<String,String> getInput(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();
        System.out.println(formData);
        return parseFromData(formData);
    }

    private void displayLoginPage(HttpExchange httpExchange) throws IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("static/index.html");
        JtwigModel model = JtwigModel.newModel();
        String response = template.render(model);

        executeResponse(httpExchange, response, response.length());
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

    private Map<String,String> parseFromData(String formData) throws UnsupportedEncodingException {
        Map<String,String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            String value = URLDecoder.decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }
}
