package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoClass{

    public CodecoolClass getClassById(int id);
    public void createClass(String name);
    public ArrayList <CodecoolClass> importData();
    public void exportData(ArrayList <CodecoolClass> updatedCodecoolClass);

}