package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoMentor {

    public Mentor getMentorById(int id);
    public void createMentor(String name, String password, String email, int userId, int classId);
    public ArrayList <Mentor> importData();
    public void exportData(ArrayList <Mentor> updatedMenotrs);

}