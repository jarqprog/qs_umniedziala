package dao;

import java.util.*;

public interface IDao{

    public void importData();
    public void exportData();
    public ArrayList<Object> exctractImportedData(ArrayList<String> list);
    public ArrayList<String> packDataToExport(ArrayList<Object> list);

}