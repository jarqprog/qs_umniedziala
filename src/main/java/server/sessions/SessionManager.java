package server.sessions;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.util.*;

/**
 *    sample usage (in handler class):
 *
 *    if(! sessionManager.validate(httpExchange)) {  //  if there is no valid session for logged user
 *        int userId = user.getUserId();
 *        sessionManager.register(httpExchange, userId);
 *    }
 * in case of logout - use:
 *    sessionManager.remove(httpExchange)
 */

public class SessionManager implements ISessionManager {

    private Map<String,Calendar> sessions;
    private static ISessionManager instance;
    private final long expirationTime;  // in milliseconds (recommended: 300000)

    // singleton:
    public static ISessionManager create(long expirationTime) {
        if(instance == null) {
            instance = new SessionManager(expirationTime);
        }
        return instance;
    }

    private SessionManager(long expirationTime) {
        sessions = new HashMap<>();
        this.expirationTime = expirationTime;
    }

    @Override
    public boolean validate(HttpExchange he) {
        deleteExpired();  // remove old sessions
        try {
            if(isLogged(he)) {
                String sessionToken = extractToken(he);
                sessions.put(sessionToken, Calendar.getInstance());  // time has been updated (session is current)
                return true;
            }
            return false;
        } catch (SessionException notUsed) {
            return false;
        }
    }

    @Override
    public boolean remove(HttpExchange he) {
        try {
            String sessionToken = extractToken(he);
            sessions.remove(sessionToken);
            return true;
        } catch (SessionException notUsed) {
            return false;
        }
    }

    @Override
    public boolean register(HttpExchange he, int userId) {
        String prefix = String.valueOf(getRandomNumber());
        String sessionId = prefix + "==" + userId;
        he.getResponseHeaders().set("Set-Cookie", "sessionToken="+sessionId);
        sessions.put(sessionId, Calendar.getInstance());
        return true;
    }

    private int deleteExpired() {
        Map<String,Calendar> copy = new HashMap<>();  // copy to avoid ConcurrentModificationException
        int sessionsDeleted = 0;
        long timeNow = Calendar.getInstance().getTimeInMillis();
        for(Map.Entry<String,Calendar> session : sessions.entrySet()) {
            long sessionTime = session.getValue().getTimeInMillis();
            if((timeNow - sessionTime) < expirationTime) {
                copy.put(session.getKey(), session.getValue());
            } else {
                sessionsDeleted++;
            }
        }
        sessions = copy;
        return sessionsDeleted;
    }

    private int getRandomNumber() {
        return new Random().nextInt(10000);
    }

    private String extractToken(HttpExchange he) throws SessionException {
        String cookieMatcher = "sessionToken=";

        try {
            Headers headers = he.getRequestHeaders();
            List<String> cookies = headers.get("Cookie");
            String cookie = cookies.stream()
                    .filter(c -> c.contains(cookieMatcher))
                    .findFirst().orElse("");

            return cookie.replace(cookieMatcher, "");
        } catch (Exception notUsed) {
            throw new SessionException();
        }
    }

    private boolean isLogged(HttpExchange he) {
        try {
            String sessionToken = extractToken(he);
            return sessions.containsKey(sessionToken);

        } catch (SessionException notUsed) {
            return false;
        }
    }
}