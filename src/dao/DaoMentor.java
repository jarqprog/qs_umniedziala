package dao;

import model.*;
import java.util.ArrayList;

public class DaoMentor extends Dao {

    public Mentor createMentor(String name, String password, String email) {
        return new Mentor(name, password, email);
    }

    public Mentor createMentor(int userId, String name, String password, String email) {
        return new Mentor(userId, name, password, email);
    }


}
