package server.factory;

import server.webcontrollers.IServerController;

public interface IControllerFactory {

    <T extends IServerController> T create(Class<T> controllerType);
}
