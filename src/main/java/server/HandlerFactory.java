package server;

import com.sun.net.httpserver.HttpHandler;
import system.dao.DaoLogin;
import system.dao.IDaoFactory;
import server.factory.IControllerFactory;
import server.factory.IHandlerFactory;
import server.helpers.IResponseManager;
import server.sessions.ISessionManager;
import server.webcontrollers.WebAdminController;
import server.webcontrollers.WebMentorController;
import server.webcontrollers.WebStudentController;

public class HandlerFactory implements IHandlerFactory {

    private final IControllerFactory controllerFactory;
    private final IResponseManager responseManager;
    private final ISessionManager sessionManager;
    private final IDaoFactory daoFactory;


    public static IHandlerFactory getInstance(IControllerFactory controllerFactory, IResponseManager responseManager,
                                              ISessionManager sessionManager, IDaoFactory daoFactory) {
        return new HandlerFactory(controllerFactory, responseManager, sessionManager, daoFactory);
    }

    private HandlerFactory(IControllerFactory controllerFactory, IResponseManager responseManager,
                           ISessionManager sessionManager, IDaoFactory daoFactory) {
        this.controllerFactory = controllerFactory;
        this.responseManager = responseManager;
        this.sessionManager = sessionManager;
        this.daoFactory = daoFactory;
    }

    @Override
    public <T extends HttpHandler> T create(Class<T> handlerType) {
        String handlerName = handlerType.getSimpleName();
        HttpHandler handler = null;

        switch (handlerName) {
            case ("Static"):
                handler = Static.create();
                break;
            case ("Login"):
                handler = Login.create(daoFactory.create(DaoLogin.class), sessionManager);
                break;
            case ("AdminHandler"):
                handler = AdminHandler.create(sessionManager,
                        controllerFactory.create(WebAdminController.class),
                        responseManager);
                break;
            case ("MentorHandler"):
                handler = MentorHandler.create(sessionManager,
                        controllerFactory.create(WebMentorController.class),
                        responseManager);
                break;
            case ("StudentHandler"):
                handler = StudentHandler.create(sessionManager,
                        controllerFactory.create(WebStudentController.class),
                        responseManager);
                break;
            case ("Logout"):
                handler = Logout.create(sessionManager);
                break;
            }
        return handlerType.cast(handler);
    }
}


