package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.IDaoLogin;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;

public class Login implements HttpHandler {

    public Login(IDaoLogin loginDao ) {
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
     String response =   httpExchange.getRequestMethod();
     if(response.equals("GET")){
         JtwigTemplate template = JtwigTemplate.classpathTemplate("static/index.html");
         JtwigModel model = JtwigModel.newModel();
          response = template.render(model);
          httpExchange.sendResponseHeaders(200,response.length());
         OutputStream os = httpExchange.getResponseBody();
         os.write(response.getBytes());
         os.close();

         }
     }

    }

