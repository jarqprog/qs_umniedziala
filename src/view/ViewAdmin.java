package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ViewAdmin extends View {

    private static ArrayList<String> adminOptions = new ArrayList<String>(Arrays.asList("1. Create mentor",
            "2. Create class", "3. Edit mentor", "4. See mentor's profile", "5. Assign mentor to class",
            "6. Create levels of experience", "0. Log out"));

    private static ArrayList <String> editMentorOptions1 = new ArrayList <String>(Arrays.asList("1. Edit mentor's e-mail",
            "2. Edit mentor's class", "0. Go back" ));

    private static ArrayList<String> editMentorOptions = new ArrayList<String>(Arrays.asList("1. Unsign mentor from class",
            "2. Change mentor class", "0. Log out"));

    public ArrayList<String> getAdminOptions() {
        return adminOptions;
    }

    public ArrayList<String> getEditMentorOptions1() {
        return editMentorOptions1;
    }


    public ArrayList<String> getEditMentorOptions() {
        return editMentorOptions;
    }

}

