package server.helpers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

public interface IResponseManager {

    void executeResponse(HttpExchange httpExchange, String response) throws IOException;
    Map<String,String> getInput(HttpExchange httpExchange) throws IOException;
    void redirectToLogin(HttpExchange httpExchange) throws IOException;
    Map parseEditData(HttpExchange httpExchange) throws IOException;
}
