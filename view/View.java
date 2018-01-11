package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.stream.*;
import java.util.ArrayList;
import java.util.List;

import iterator.*;


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

    public int getIntInputFromUser(String request){

        int number;

        while (true){
            try{
                System.out.println(request);
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String userInput = reader.readLine();
                number = Integer.parseInt(userInput);
                return number;
                }
            catch (IOException | NumberFormatException e){
                System.out.println("Wrong input");
                continue;
            }
        }
    }

    public void displayText(String text){
        System.out.println(text);
    }

    public void displayList(ArrayList <String> list){

        MyIterator <String> iterator = new MyIterator(list);
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

    
}