package controller;

import model.Admin;
import view.ViewAdmin;

public class ControllerAdmin{

    private ViewAdmin viewAdmin;
    private Admin admin;

    public ControllerAdmin(Admin admin) {
        this.viewAdmin = new ViewAdmin();
        this.admin = admin;
    }

}
