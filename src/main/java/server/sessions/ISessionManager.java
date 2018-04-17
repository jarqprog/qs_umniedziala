package server.sessions;

import com.sun.net.httpserver.HttpExchange;

public interface ISessionManager {

//    boolean isLogged(HttpExchange he);  // has user
    boolean validate(HttpExchange he);
    boolean register(HttpExchange he, String userUniqueData);  // userUniqueData - something unique, e.g. email
    boolean remove(HttpExchange he);  // in case of logout
}
