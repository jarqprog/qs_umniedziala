package server.helpers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseManager implements IResponseManager {

    public static IResponseManager create() {
        return new ResponseManager();
    }

    private ResponseManager() {}

    @Override
    public void executeResponse(HttpExchange httpExchange, String response) throws IOException {
        byte[] bytes = response.getBytes();
        httpExchange.sendResponseHeaders(200, bytes.length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
