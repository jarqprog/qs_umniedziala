package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoMentor extends IDao{

    public ArrayList<Mentor> getMentors();
    public Mentor getMentorById(int id);
    public Mentor createMentor();

}