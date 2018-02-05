package dao;

import java.util.ArrayList;
import dao.DaoStudent;
import model.*;

public class DaoClass{

    public CodecoolClass createClass(String name){
        return new CodecoolClass(name);
    }

}