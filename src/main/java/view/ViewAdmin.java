package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewAdmin extends View {

    private List<String> adminOptions = new ArrayList<String>(Arrays.asList("1. Create mentor",
            "2. Create class", "3. Edit mentor", "4. See mentor's profile",
            "5. Create levels of experience", "0. Log out"));

    private List <String> editMentorOptions1 = new ArrayList <String>(Arrays.asList("1. Edit mentor's e-mail",
            "2. Edit mentor's class", "0. Go back" ));

    private List<String> editMentorOptions = new ArrayList<String>(Arrays.asList("1. Unsign mentor from class",
            "2. Change mentor class", "0. Log out"));

    public List<String> getAdminOptions() {
        return adminOptions;
    }

    public List<String> getEditMentorOptions1() {
        return editMentorOptions1;
    }


    public List<String> getEditMentorOptions() {
        return editMentorOptions;
    }

}

