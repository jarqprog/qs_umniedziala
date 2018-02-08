package view;

import java.util.ArrayList;
import java.util.Arrays;


public class ViewMentor extends View{

    private static ArrayList <String> mentorOptions = new ArrayList <String>(Arrays.asList("1. Create student",
        "2. Create team", "3. Create quest", "4. Add artifact to store", "5. Edit quest",
        "6. Edit artifact", "7. Mark quest", "8. Mark artifact", "9. See student's wallet",
        "10. Assign student to team", "0. Go back"));

    private static ArrayList <String> assignStudentToTeamOptions = new ArrayList <String>(Arrays.asList("1. Assign student to chosen team", "0. Go back"));

    private static ArrayList <String> chooseTeamOrStudent = new ArrayList<>(Arrays.asList("1. Mark team quest", "2. Mark sigle student quest",
            "0. Go back"));

    public ArrayList<String> getMentorOptions() { return mentorOptions; }

    public ArrayList<String> getAssignStudentToTeamOptions() { return assignStudentToTeamOptions; }

    public static ArrayList<String> getChooseTeamOrStudent() { return chooseTeamOrStudent; }
}
