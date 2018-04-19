package server;

import java.io.IOException;

public class ServerClient implements IClient {

    public static IClient create() {
        return new ServerClient();
    }

    // todo - implement log (to register exceptions)

    private ServerClient() {}

    @Override
    public void runClient() {

        try {
            Server.getInstance(8080).run();
            System.out.println("http://localhost:8080/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
