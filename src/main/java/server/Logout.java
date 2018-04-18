package server;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import server.sessions.ISessionManager;
import java.io.*;

public class Logout implements HttpHandler {

    private final ISessionManager sessionManager;

    public static HttpHandler create(ISessionManager sessionManager) {
        return new Logout(sessionManager);
    }

    private Logout(ISessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        sessionManager.remove(httpExchange);
        displayLogoutPage(httpExchange);
    }

    private void executeResponse(HttpExchange httpExchange, String response, int i) throws IOException {
        httpExchange.sendResponseHeaders(200, i);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void displayLogoutPage(HttpExchange httpExchange) throws IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("static/logout.html.twig");
        JtwigModel model = JtwigModel.newModel();
        String response = template.render(model);
        executeResponse(httpExchange, response, response.length());
    }
}
