package server.sessions;

import com.sun.net.httpserver.HttpExchange;

public interface ISessionManager {

    int getCurrentUserId(HttpExchange he);  //  use it in concrete user handler (eg. AdminHandler)
    boolean register(HttpExchange he, int userId);  // use it while login (if user is verified)
    boolean remove(HttpExchange he);  // use it in case of logout

}
