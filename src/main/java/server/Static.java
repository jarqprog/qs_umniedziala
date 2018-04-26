package server;


import server.helpers.MimeTypeResolver;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URI;
import java.net.URL;

public class Static implements HttpHandler {

    public static HttpHandler create() {
        return new Static();
    }

    private Static() {}

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        // get file path from url
        URI uri = httpExchange.getRequestURI();

        String path = uri.getPath().substring(1);

        ClassLoader classLoader = getClass().getClassLoader();

        InputStream inputStream = classLoader.getResourceAsStream(path);
        URL fileURL = classLoader.getResource(path);

        if (inputStream == null || fileURL == null) {
            // Object does not exist or is not a file: reject with 404 error.
            send404(httpExchange);
        } else {
            // Object exists and is a file: accept with response code 200.
            sendFile(httpExchange, inputStream, fileURL);
        }
    }

    private void send404(HttpExchange httpExchange) throws IOException {
        String response = "404 (Not Found)\n";
        httpExchange.sendResponseHeaders(404, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void sendFile(HttpExchange httpExchange, InputStream inputStream, URL fileURL) throws IOException {

        File file = new File(fileURL.getFile());

        // we need to find out the mime type of the file, see: https://en.wikipedia.org/wiki/Media_type
        MimeTypeResolver resolver = new MimeTypeResolver(file);
        String mime = resolver.getMimeType();

        httpExchange.getResponseHeaders().set("Content-Type", mime);
        httpExchange.sendResponseHeaders(200, 0);

        OutputStream os = httpExchange.getResponseBody();

        // send the file
        final byte[] buffer = new byte[0x10000];
        int count;
        while ((count = inputStream.read(buffer)) >= 0) {
            os.write(buffer, 0, count);
        }
        os.close();
    }
}
