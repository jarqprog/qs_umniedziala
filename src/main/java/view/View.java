package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.stream.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

import iterator.*;


public abstract class View{

    public String getInputFromUser(String request){
        String userInput ="";
        do{
            try {
                System.out.print(request);
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                userInput = reader.readLine();
                }
            catch (IOException e) {
                displayText("Wrong input");
                }
        }while(userInput.trim().isEmpty());

    return userInput;
    }

    public int getIntInputFromUser(String request){

        int number = 0;
        boolean correctInput = true;

        while (correctInput){
            try{
                System.out.print(request);
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String userInput = reader.readLine();
                number = Integer.parseInt(userInput);
                if(number > 0) {
                    correctInput = false;
                }else {
                    displayText("No non-positive values allowed");
                }
            }
            catch (IOException | NumberFormatException e){
                displayText("Wrong input");
                continue;
            }
        }
        return number;
    }

    public void displayText(String text){
        System.out.println(text);
    }

    public <T> void displayList(ArrayList <T> list){

        MyIterator <T> iterator = new MyIterator <T>(list);
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

}