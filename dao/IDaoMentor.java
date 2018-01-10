package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoMentor {

    public ArrayList<Mentor> getMentors();
    public Mentor getMentorById(int id);
    public Mentor createMentor(String name, String password, String email);
    public void importData(Mentor mentor);
    public void exportData();

}