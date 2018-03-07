package dao;

import model.Student;
import model.Team;

import java.util.ArrayList;

public interface IDaoTeam {
    Team createTeam(String name);

    Team createTeam (int groupId, String name, ArrayList<Student> students, int availableCoins);

    Team importTeam(int teamId);

    boolean exportTeam(Team team);

    void updateTeamData(Team team);

    Team getTeamByStudentId(Integer studentId);

    ArrayList<Student> getStudentsOfTeam(int teamId);

    ArrayList<Team> getAllTeams();

    void assignStudentToTeam(int studentId, int teamId);
}
