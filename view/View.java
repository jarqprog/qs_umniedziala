package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.stream.*;
import java.util.ArrayList;


public abstract class View{

    public String getInputFromUser(String request){
        try {
            System.out.println(request);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String userInput = reader.readLine();
            return userInput;
            
            } 
        catch (IOException e) {
            System.out.println("Wrong input");
            }  
        return null;    
    }

    public void displayText(String text){
        System.out.println(text);
    }

    public void displayList(ArrayList <String> list){
        for(String text: list){
            System.out.println(text);
        }
    }

    
}