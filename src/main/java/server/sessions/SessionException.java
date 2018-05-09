package server.sessions;

class SessionException extends Exception {

    private String message;

    SessionException() {
        message = "problem with session occurred";
    }

}
