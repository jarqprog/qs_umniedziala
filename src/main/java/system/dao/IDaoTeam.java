package system.dao;

import system.model.Student;
import system.model.Team;

import java.util.List;

public interface IDaoTeam {

    Team createTeam(String name);

    Team importTeam(int teamId);

    boolean updateTeamData(Team team);

    Team getTeamByStudentId(int studentId);

    List<Student> getStudentsOfTeam(int teamId);

    List<Team> getAllTeams();

    boolean assignStudentToTeam(int studentId, int teamId);
}
