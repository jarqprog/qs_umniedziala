package controller;

import model.Student;
import view.ViewStudent;

public class ControllerStudent{

    private ViewStudent viewStudent;
    private Student student;

    public ControllerStudent(Student student) {
        this.viewStudent = new ViewStudent();
        this.student = student;
    }

    public void seeWallet() {

    }

    public void buyArtifact() {

    }

    public void seeExpLevel() {

    }

    public void manageTeam() {

    }

    public void seeQuests() {

    }

    public void runMenu() {

        String studentOption = "";
        while (!studentOption.equals("0")) {

            System.out.println("\nWhat would like to do?");
            viewStudent.displayList(viewStudent.getStudentOptions());

            studentOption = viewStudent.getInputFromUser("Option: ");
            switch (studentOption) {
                case "1": seeWallet();
                        break;
                case "2": buyArtifact();
                        break;
                case "3": seeExpLevel();
                        break;
                case "4": manageTeam();
                        break;
                case "5": seeQuests();
                        break;
                case "6": seeArtifacts();
                        break;
                case "0": break;

                default: System.out.println("Wrong option. Try again!");
                         break;
            }
        }

    }

}
