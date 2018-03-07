package view;

import java.util.ArrayList;
import java.util.Arrays;


public class ViewMentor extends View{

    private ArrayList <String> mentorOptions = new ArrayList <String>(Arrays.asList("1. Create student",
        "2. Create team", "3. Create quest", "4. Add artifact to store", "5. Edit quest",
        "6. Edit artifact", "7. Mark quest", "8. Mark artifact", "9. See students' wallet",
        "10. Assign student to team", "0. Go back"));

    private ArrayList <String> assignStudentToTeamOptions = new ArrayList <String>(Arrays.asList("1. Assign student to chosen team", "0. Go back"));

    private ArrayList <String> chooseTeamOrStudent = new ArrayList<>(Arrays.asList("1. Mark team quest", "2. Mark sigle student quest",
            "0. Go back"));

    private ArrayList <String> artifactEditOptions = new ArrayList<>(Arrays.asList("1. Change name",
            "2. Change value", "3. Change description", "4. Change type", "5. Change category"));

    private ArrayList <String> updateArtifactsOptions = new ArrayList <String>(Arrays.asList("1. Update artifacts name",
            "2. Update artifacts description", "3. Update artifacts value", "4. Update artifacts type",
            "0. Go back"));

    private ArrayList <String> updateArtifactTypeOptions = new ArrayList <String>(Arrays.asList("1. Type individual",
            "2. Type team", "0. Go back"));

    private ArrayList <String> questEditOptions = new ArrayList<>(Arrays.asList("1. Change name",
            "2. Change description", "3. Change value", "4. Change type", "5. Change category", "0. Go back"));

    public ArrayList<String> getMentorOptions() {
        return mentorOptions;
    }

    public ArrayList<String> getChooseTeamOrStudent() { return chooseTeamOrStudent; }

    public ArrayList<String> getArtifactEditOptions() {
        return artifactEditOptions;
    }

    public ArrayList<String> getEditQuestOptions() {
        return questEditOptions;
    }

    public ArrayList<String> getUpdateArtifactsOptions() {
        return updateArtifactsOptions;
    }

    public ArrayList<String> getUpdateArtifactTypeOptions() {
        return updateArtifactTypeOptions;
    }

    public ArrayList<String> getAssignStudentToTeamOptions() { return assignStudentToTeamOptions; }

}
