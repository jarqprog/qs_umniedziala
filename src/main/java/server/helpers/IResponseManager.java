package server.helpers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public interface IResponseManager {

    void executeResponse(HttpExchange httpExchange, String response) throws IOException;
}
