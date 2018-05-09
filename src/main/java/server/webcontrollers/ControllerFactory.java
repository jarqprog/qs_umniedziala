package server.webcontrollers;

import dao.*;
import server.factory.IControllerFactory;

public class ControllerFactory implements IControllerFactory {

    private final IDaoFactory daoFactory;

    public static IControllerFactory getInstance(IDaoFactory daoFactory) {
        return new ControllerFactory(daoFactory);
    }

    private ControllerFactory(IDaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public <T extends IServerController> T create(Class<T> controllerType) {
        String controllerName = controllerType.getSimpleName();
        IServerController controller = null;

        switch(controllerName) {
            case("WebAdminController"):
                controller = WebAdminController
                    .create(daoFactory.create(DaoAdmin.class), daoFactory.create(DaoMentor.class),
                            daoFactory.create(DaoClass.class), daoFactory.create(DaoLevel.class));
                break;
            case("WebMentorController"):
                controller = WebMentorController
                    .create(daoFactory.create(DaoWallet.class), daoFactory.create(DaoStudent.class),
                            daoFactory.create(DaoArtifact.class), daoFactory.create(DaoLevel.class),
                            daoFactory.create(DaoTeam.class), daoFactory.create(DaoClass.class),
                            daoFactory.create(DaoQuest.class), daoFactory.create(DaoMentor.class));
                break;
            case("WebStudentController"):
                controller = WebStudentController
                    .create(daoFactory.create(DaoWallet.class), daoFactory.create(DaoStudent.class),
                            daoFactory.create(DaoArtifact.class), daoFactory.create(DaoLevel.class),
                            daoFactory.create(DaoTeam.class), daoFactory.create(DaoClass.class));
                break;
        }
        return controllerType.cast(controller);
    }
}
