package server.sessions;

import com.sun.net.httpserver.HttpExchange;

public interface ISessionManager {

    boolean validate(HttpExchange he);
    boolean register(HttpExchange he, int userId);
    boolean remove(HttpExchange he);  // in case of logout
}
