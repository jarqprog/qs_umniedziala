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
        String response;

        if (method.equals("GET")) {
            JtwigTemplate template = JtwigTemplate.classpathTemplate("static/index.html.twig");
            JtwigModel model = JtwigModel.newModel();
            response = template.render(model);
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if (method.equals("POST")) {
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            System.out.println(formData);
            Map inputs = parseFormData(formData);

            User user = getUser(inputs.get("email").toString(), inputs.get("password").toString());

            String status = getUserRole(user);

            JtwigTemplate template = JtwigTemplate.classpathTemplate("static/user-"+status+"/"+status+"_profile.html.twig");

            JtwigModel model = JtwigModel.newModel();

            setModel(model, status, user);

            response = template.render(model);

            try {
                httpExchange.sendResponseHeaders(200, response.length() + 1);
                OutputStream os = httpExchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setModel(JtwigModel model, String status, User user) {
        if (status.equals("admin")) {
            AdminPage.setModel(user, model);
        } else if (status.equals("mentor")){
            MentorPage.setModel(user, model);
        } else if (status.equals("student")) {
            StudentPage.setModel(user, model);
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

