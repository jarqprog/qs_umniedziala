package dao;

import model.Student;
import model.Team;

import java.util.List;

public interface IDaoTeam {
    Team createTeam(String name);

    Team createTeam (int groupId, String name, List<Student> students, int availableCoins);

    Team importTeam(int teamId);

    boolean exportTeam(Team team);

    void updateTeamData(Team team);

    Team getTeamByStudentId(Integer studentId);

    List<Student> getStudentsOfTeam(int teamId);

    List<Team> getAllTeams();

    void assignStudentToTeam(int studentId, int teamId);
}
