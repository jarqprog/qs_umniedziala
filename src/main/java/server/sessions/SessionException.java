package server.sessions;

public class SessionException extends Exception {

    private String message;

    public SessionException() {
        message = "problem with session occurred";
    }

}
