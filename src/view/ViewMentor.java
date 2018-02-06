package view;

import java.util.ArrayList;
import java.util.Arrays;


public class ViewMentor extends View{

    private static ArrayList <String> mentorOptions = new ArrayList <String>(Arrays.asList("1. Create student",
        "2. Create team", "3. Create quest", "4. Add artifact to store", "5. Edit quest",
        "6. Edit artifact", "7. Mark quest", "8. Mark artifact", "9. See student's wallet",
        "0. Log out"));

    private static ArrayList <String> artifactEditOptions = new ArrayList<>(Arrays.asList("1. Change name",
            "2. Change value", "3. Change description", "4. Change type", "5. Change category"));

    public ArrayList<String> getMentorOptions() {
        return mentorOptions;
    }

    public static ArrayList<String> getArtifactEditOptions() {
        return artifactEditOptions;
    }
}
