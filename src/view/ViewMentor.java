package view;

import java.util.ArrayList;
import java.util.Arrays;


public class ViewMentor extends View{

    private static ArrayList <String> mentorOptions = new ArrayList <String>(Arrays.asList("1. Create student",
        "2. Create team", "3. Create quest", "4. Add artifact to store", "5. Edit quest",
        "6. Edit artifact", "7. Mark quest", "8. Mark artifact", "9. See student's wallet",
        "0. Log out"));

    private static ArrayList <String> questEditOptions = new ArrayList<>(Arrays.asList("1. Change name",
            "2. Change value", "3. Change description", "4. Change type", "5. Change category"));

    private static ArrayList <String> updateQuestOptions = new ArrayList <String>(Arrays.asList("1. Update quest's name",
            "2. Update quest's description", "3. Update quest's value", "4. Update quest's type", "5. Update quest's category",
            "0. Go back"));

    private static ArrayList <String> updateQuestTypeOptions = new ArrayList <String>(Arrays.asList("1. Type individual",
            "2. Type team", "0. Go back"));
    public ArrayList<String> getMentorOptions() {
        return mentorOptions;
    }

}
