package server.factory;

import com.sun.net.httpserver.HttpHandler;

public interface IHandlerFactory {

    <T extends HttpHandler> T create(Class<T> handlerType);
}
